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

    public Parcela(Long id, StatusParcela statusParcela, LocalDate competencia, Matricula matricula, Integer numeroParcela, LocalDate vencimentoParcela, BigDecimal valorParcela) {
        this.id = id;
        this.statusParcela = statusParcela;
        this.competencia = competencia;
        this.matricula = matricula;
        this.numeroParcela = numeroParcela;
        this.vencimentoParcela = vencimentoParcela;
        this.valorParcela = valorParcela;
    }

    public Parcela() {
    }

    public BigDecimal aplicarPagamento(BigDecimal totalPago) {
        if (this.statusParcela.equals(StatusParcela.PAGA)) {
            throw new BusinessException("Parcela ja paga");
        }
        if (totalPago.compareTo(this.valorParcela) == 0) {
            return BigDecimal.ZERO;
        } else if (totalPago.compareTo(this.valorParcela) == 1) {
            if (this.matricula.getParcelas().stream().filter(parcela -> !parcela.getStatusParcela().equals(StatusParcela.PAGA)).count() > 0) {
                BigDecimal resto = totalPago.subtract(this.valorParcela);
                return resto;
            } else {
                //Parei aqui
                //Criar Repository, DTOs
            }
        }
    }

    //aplicarPagamento(BigDecimal totalPago): compara com valorParcela e define o status (paga, parcialmente paga ou em aberto).
    //estaVencida(): compara o vencimento com a data atual e verifica se ainda não foi paga.
    //estaEmAberto(): evita repetir a comparação de status pelo código.
    //cancelar(): muda o status, com regra de que parcela já paga não pode ser cancelada.
}
