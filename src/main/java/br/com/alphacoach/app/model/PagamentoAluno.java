package br.com.alphacoach.app.model;
import br.com.alphacoach.app.model.enums.FormaPagamento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    //adicionarPagamentoParcela(Parcela, BigDecimal valor): cria o PagamentoParcela, preenche setPagamentoAluno(this) e adiciona na lista. Vale validar aqui que a soma dos valores aplicados não ultrapassa valorPagamento.
    //removerPagamentoParcela(PagamentoParcela): usado em estorno ou ajuste.
    //getValorAplicadoTotal(): soma dos valorAplicado da lista. Como a lista pertence a este agregado, a soma em memória é segura.
    //getValorDisponivel(): valorPagamento menos o total aplicado. Ajuda a distribuir o valor entre as parcelas.

}
