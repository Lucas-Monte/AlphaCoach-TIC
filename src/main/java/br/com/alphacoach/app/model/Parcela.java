package br.com.alphacoach.app.model;

import br.com.alphacoach.app.exception.BusinessException;
import br.com.alphacoach.app.model.enums.StatusParcela;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "parcela")
public class Parcela {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "status_parcela")
    @Enumerated(EnumType.STRING)
    private StatusParcela statusParcela;
    @Column(name = "competencia")
    private LocalDate competencia;
    @ManyToOne
    @JoinColumn(name = "matricula_id")
    private Matricula matricula;
    @Column(name = "numero_parcela")
    private Integer numeroParcela;
    @Column(name = "vencimento_parcela")
    private LocalDate vencimentoParcela;
    @Column(name = "valor_parcela", precision = 10, scale = 2)
    private BigDecimal valorParcela;

    public Parcela(LocalDate competencia, Integer numeroParcela, LocalDate vencimentoParcela, BigDecimal valorParcela) {
        this.competencia = competencia;
        this.numeroParcela = numeroParcela;
        this.vencimentoParcela = vencimentoParcela;
        this.valorParcela = valorParcela;
    }

    public Parcela() {
    }

    public void pagar() {
        if (statusParcela == StatusParcela.PAGA) {
            throw new BusinessException("Parcela já está paga");
        }

        if (!estaEmAberto()) {
            throw new BusinessException("Parcela cancelada não pode ser paga");
        }

        statusParcela = StatusParcela.PAGA;
    }

    public void estornarPagamento() {
        if (statusParcela != StatusParcela.PAGA) {
            throw new BusinessException("Apenas parcelas pagas podem ser estornadas");
        }
        statusParcela = vencimentoParcela.isBefore(LocalDate.now())
                ? StatusParcela.ATRASADA
                : StatusParcela.EM_DIA;
    }

    public void alterarVencimento(LocalDate novoVencimento) {
        if (novoVencimento == null) {
            throw new BusinessException("O novo vencimento é obrigatório");
        }
        if (!estaEmAberto()) {
            throw new BusinessException("Só é possível alterar o vencimento de parcela em aberto");
        }
        this.vencimentoParcela = novoVencimento;
        this.statusParcela = novoVencimento.isBefore(LocalDate.now())
                ? StatusParcela.ATRASADA
                : StatusParcela.EM_DIA;
    }

    public boolean estaVencida() {
        return estaEmAberto() && vencimentoParcela.isBefore(LocalDate.now());
    }

    public boolean estaEmAberto() {
        return statusParcela != StatusParcela.PAGA && statusParcela != StatusParcela.CANCELADA;
    }

    public void atualziarStatusPorVencimento() {
        if (statusParcela == StatusParcela.EM_DIA && estaVencida()) {
            statusParcela = StatusParcela.ATRASADA;
        }
    }

    public void cancelar() {
        if (statusParcela == StatusParcela.CANCELADA) {
            throw new BusinessException("Parcela ja cancelada.");
        }
        if (statusParcela == StatusParcela.PAGA) {
            throw new BusinessException("Parcela com pagamento não pode ser cancelada. Estorne os pagamentos antes.");
        }
        statusParcela = StatusParcela.CANCELADA;
    }

    //aplicarPagamento(BigDecimal totalPago): compara com valorParcela e define o status (paga, parcialmente paga ou em aberto).
    //estaVencida(): compara o vencimento com a data atual e verifica se ainda não foi paga.
    //estaEmAberto(): evita repetir a comparação de status pelo código.
    //cancelar(): muda o status, com regra de que parcela já paga não pode ser cancelada.
}
