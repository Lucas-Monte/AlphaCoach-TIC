package br.com.alphacoach.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    @Column (nullable = false)
    private Float valor;
    @Column (nullable = false)
    private Integer duracaoMeses;
    @Column
    private String tipoPlano;
    @Column
    private Boolean ativo;

    public Planos(Long id, String descricao, Float valor, Integer duracaoMeses, String tipoPlano) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.duracaoMeses = duracaoMeses;
        this.tipoPlano = tipoPlano;
        this.ativo = true;
    }

    public Planos() {
    }

    public Float calcularValorMensal() {
        return this.getValor() / this.getDuracaoMeses();
    }

    public Float aplicarDesconto(float percentual) {
        return this.valor - (valor * percentual);
    }
}
