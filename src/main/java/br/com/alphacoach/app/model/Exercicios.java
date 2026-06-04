package br.com.alphacoach.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "exercicios")
public class Exercicios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String nome;
    @Column(length = 200)
    private String descricao;
    @Column
    private Boolean ativo;
    @Column (length = 500)
    private String linkVideo;

    public Exercicios(Long id, String nome, String descricao, String linkVideo) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.ativo = true;
        this.linkVideo = linkVideo;
    }

    public Exercicios() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void removerExercicio(long id) {
        this.ativo = false;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getLinkVideo() {
        return linkVideo;
    }

    public void setLinkVideo(String linkVideo) {
        this.linkVideo = linkVideo;
    }
}
