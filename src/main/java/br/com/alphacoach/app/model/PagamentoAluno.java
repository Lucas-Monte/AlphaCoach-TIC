package br.com.alphacoach.app.model;
import br.com.alphacoach.app.exception.BusinessException;
import br.com.alphacoach.app.model.enums.FormaPagamento;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name="pagamento_aluno")
public class PagamentoAluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;
    @Column(name = "data_pagamento", nullable = false)
    private LocalDate dataPagamento;
    @Column(name = "valor_pagamento", precision = 10, scale = 2)
    private BigDecimal valorPagamento;
    @Column(name = "forma_pagamento")
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "pagamentoAluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PagamentoParcela> pagamentoParcelas = new ArrayList<>();


    public PagamentoAluno(Long id, Aluno aluno, LocalDate dataPagamento, BigDecimal valorPagamento, FormaPagamento formaPagamento) {
        this.id = id;
        this.aluno = aluno;
        this.dataPagamento = dataPagamento;
        this.valorPagamento = valorPagamento;
        this.formaPagamento = formaPagamento;
    }

    public PagamentoAluno() {
    }

    public BigDecimal getValorAplicadoTotal() {
        return pagamentoParcelas.stream()
                .map(PagamentoParcela::getValorAplicado)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getValorDisponivel() {
        if (valorPagamento == null) {
            return BigDecimal.ZERO;
        }
        return valorPagamento.subtract(getValorAplicadoTotal());
    }

    public boolean estaTotalmenteAplicado() {
        return getValorAplicadoTotal().signum() == 0;
    }

    public PagamentoParcela adicionarPagamentoParcela(Parcela parcela) {
        if (parcela == null) {
            throw new BusinessException("A parcela é obrigatória");
        }

        boolean parcelaJaVinculada = pagamentoParcelas.stream()
                .anyMatch(item -> item.getParcela().equals(parcela));
        if (parcelaJaVinculada) {
            throw new IllegalStateException("Esta parcela ja está vinculada a este pagamento.");
        }

        BigDecimal valor = parcela.getValorParcela();
        if (valor.compareTo(getValorDisponivel()) > 0) {
            throw new BusinessException(
                    "O valor da parcela (" + valor + ") ultrapassa o valor disponível do pagamento ("
                            + getValorDisponivel() + ").");
        }

        parcela.pagar();

        PagamentoParcela item = new PagamentoParcela();
        item.setParcela(parcela);
        item.setValorAplicado(valor);
        item.setPagamentoAluno(this);

        pagamentoParcelas.add(item);
        return item;

    }

    public void removerPagamentoParcela(PagamentoParcela item) {
        if (item == null || !pagamentoParcelas.contains(item)) {
            throw new BusinessException("O item informado não pertence a este pagamento");
        }
        item.getParcela().estornarPagamento();
        pagamentoParcelas.remove(item);
        item.setPagamentoAluno(null);
    }

    //adicionarPagamentoParcela(Parcela, BigDecimal valor): cria o PagamentoParcela, preenche setPagamentoAluno(this) e adiciona na lista. Vale validar aqui que a soma dos valores aplicados não ultrapassa valorPagamento.
    //removerPagamentoParcela(PagamentoParcela): usado em estorno ou ajuste.
    //getValorAplicadoTotal(): soma dos valorAplicado da lista. Como a lista pertence a este agregado, a soma em memória é segura.
    //getValorDisponivel(): valorPagamento menos o total aplicado. Ajuda a distribuir o valor entre as parcelas.


    //adicionarPagamentoParcela(Parcela) não recebe mais valor: o valor aplicado é o valorParcela. Ele valida duplicidade e saldo disponível, e só então chama parcela.pagar().
    //removerPagamentoParcela chama parcela.estornarPagamento() antes de remover. Com isso, vínculo e status não divergem, e o service não precisa lembrar de atualizar a parcela.
    //Novo estaTotalmenteAplicado(), para o service rejeitar pagamento com sobra ou falta de valor, se essa for a regra.
}
