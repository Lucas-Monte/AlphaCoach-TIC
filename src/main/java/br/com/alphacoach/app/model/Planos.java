package br.com.alphacoach.app.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Integer getDuracaoMeses() {
        return duracaoMeses;
    }

    public void setDuracaoMeses(Integer duracaoMeses) {
        this.duracaoMeses = duracaoMeses;
    }

    public String getTipoPlano() {
        return tipoPlano;
    }

    public void setTipoPlano(String tipoPlano) {
        this.tipoPlano = tipoPlano;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Float calcularValorMensal(Planos planos) {
        return planos.getValor() / planos.getDuracaoMeses();
    }

    public Float aplicarDesconto(float percentual) {
        return this.valor - (valor * percentual);
    }
}
