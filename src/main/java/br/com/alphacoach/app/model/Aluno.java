package br.com.alphacoach.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.websocket.ClientEndpoint;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "alunos")
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (nullable = false, length = 100)
    private String nome;
    @Column (length = 100)
    private String email;
    @Column (unique = true, length = 15)
    private String cpf;
    @Column (length = 500)
    private String endereco;
    @Column (nullable = false, length = 15)
    private String tipoCliente;
    @Column
    private Boolean ativo;
    @Column
    private String telefone;
    @ManyToOne
    @JoinColumn(name = "planoId")
    private Planos plano;
    @OneToMany(mappedBy = "aluno")
    @JsonIgnoreProperties("aluno")
    private List<AgendaTreino> agenda;

    public Aluno(Long id, String nome, String email, String cpf, String endereco, String tipoCliente, Boolean ativo, String telefone, Planos plano) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.endereco = endereco;
        this.tipoCliente = tipoCliente;
        this.ativo = ativo;
        this.telefone = telefone;
        this.plano = plano;
    }

    public Aluno() {
    }

    public List<AgendaTreino> getAgenda() {
        return agenda;
    }

    public void adicionaHorario(AgendaTreino agenda) {
        this.agenda.add(agenda);
    }

    public void setAgenda(List<AgendaTreino> agenda) {
        this.agenda = agenda;
    }

    public void removerHorario(Long id) {
        agenda.removeIf(horario -> Objects.equals(horario.getId(), id));
    }

    public void removerCliente(Long id) {
        if (this.id == id) {
            this.ativo = false;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Planos getPlano() {
        return plano;
    }

    public void setPlano(Planos plano) {
        this.plano = plano;
    }

    public Planos mudarPlano(Planos plano, Long id) {
        if (this.id == id) {
            return this.plano = plano;
        }
        return this.plano;
    }


}
