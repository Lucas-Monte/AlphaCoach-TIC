package br.com.alphacoach.app.model;

import br.com.alphacoach.app.exception.BusinessException;
import br.com.alphacoach.app.model.enums.TipoPlano;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "planos")
public class Planos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (nullable = false, length = 100)
    private String descricao;
    @Column (nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;
    @Column (nullable = false)
    private Integer duracaoMeses;
    @Column
    @Enumerated(EnumType.STRING)
    private TipoPlano tipoPlano;
    @Column
    private Boolean ativo;

    public Planos(Long id, String descricao, BigDecimal valor, Integer duracaoMeses, TipoPlano tipoPlano) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.duracaoMeses = duracaoMeses;
        this.tipoPlano = tipoPlano;
        this.ativo = true;
    }

    public Planos() {
    }

    public List<BigDecimal> gerarParcelas(BigDecimal valorTotal, Integer quantidade) {
        BigDecimal qtd = BigDecimal.valueOf(quantidade);
        BigDecimal valorParcela = valorTotal.divide(qtd, 2, RoundingMode.DOWN);
        BigDecimal resto = valorTotal.subtract(valorParcela.multiply(qtd));

        List<BigDecimal> parcelas = new ArrayList<>();
        for (int i = 0; i < quantidade; i++) {
            parcelas.add(valorParcela);
        }

        int ultima = quantidade - 1;
        parcelas.set(ultima, parcelas.get(ultima).add(resto));

        return parcelas;
    }

    public void ativar() {
        if (this.ativo) {
            throw new BusinessException("Plano ja está ativo");
        }
        this.ativo = true;
    }

    public void desativar() {
        if (!this.ativo) {
            throw new BusinessException("Plano ja está desativado");
        }
        this.ativo = false;
    }

    //gerarParcelas(): com BigDecimal, divide com escala e RoundingMode
    //aplicarDesconto(BigDecimal percentual): valide que está entre 0 e 100 e arredonde em 2 casas -> por enquanto não.
    //ativar() e desativar(): o desativar não deve afetar matrículas existentes, só impedir novas.
}
