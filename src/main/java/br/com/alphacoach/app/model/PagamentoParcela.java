package br.com.alphacoach.app.model;

import br.com.alphacoach.app.model.enums.StatusParcela;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.BitSet;
import java.util.Stack;

@Entity
@Getter
@Setter
@Table(name = "pagamento_parcela")
public class PagamentoParcela {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "parcela_id")
    @JsonIgnoreProperties("pagamentoParcelas")
    private Parcela parcela;
    @ManyToOne
    @JoinColumn(name = "pagamento_id")
    private PagamentoAluno pagamentoAluno;
    @Column(name = "valor_aplicado", precision = 10, scale = 2)
    private BigDecimal valorAplicado;

    public PagamentoParcela(Long id, Parcela parcela, PagamentoAluno pagamentoAluno, BigDecimal valorAplicado) {
        this.id = id;
        this.parcela = parcela;
        this.pagamentoAluno = pagamentoAluno;
        this.valorAplicado = valorAplicado;
    }

    public PagamentoParcela() {
    }

}
