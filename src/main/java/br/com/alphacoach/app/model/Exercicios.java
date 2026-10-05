package br.com.alphacoach.app.model;

import br.com.alphacoach.app.exception.BusinessException;
import br.com.alphacoach.app.exception.UrlInvalidaException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.net.URISyntaxException;

@Entity
@Getter
@Setter
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

    public void desativar() {
        if (!this.ativo) {
            throw new BusinessException("Exercício já está desativado!");
        }
        this.ativo = false;
    }

    public void ativar() {
        if (this.ativo) {
            throw new BusinessException("Exercício já está ativo!");
        }
        this.ativo = true;
    }

    public boolean validarLink(String url) {
        if (url == null || url.isBlank()) {
            return false;
        }
        try {
            URI uri = new URI(url);
            String scheme = uri.getScheme();
            return uri.getHost() != null
                    && ("http".equalsIgnoreCase(scheme) || ("https").equalsIgnoreCase(scheme));
        } catch (URISyntaxException e) {
            throw new UrlInvalidaException(e);
        }
    }


    //desativar() e ativar(): substituem o removerExercicio(long id). Um exercício já usado em treinos deve ser desativado, nunca apagado.
    //Validação de linkVideo: formato de URL básico.
}
