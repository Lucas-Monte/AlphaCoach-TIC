package br.com.alphacoach.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.websocket.ClientEndpoint;

import java.time.LocalDate;
import java.util.Date;
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
    @Column (nullable = false)
    private LocalDate dataNascimento;
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
    @Column (length = 100)
    private String objetivo;
    @Column (length = 500)
    private String anamnese;
    @OneToMany(mappedBy = "aluno")
    @JsonIgnoreProperties("aluno")
    private List<AgendaTreino> agenda;

    public Aluno(Long id, String nome, String email, String cpf, LocalDate dataNascimento, String endereco, String tipoCliente, Boolean ativo, String telefone, Planos plano, String objetivo, String anamnese) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.tipoCliente = tipoCliente;
        this.ativo = ativo;
        this.telefone = telefone;
        this.plano = plano;
        this.objetivo = objetivo;
        this.anamnese = anamnese;
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

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Boolean getAtivo() {
        return ativo;
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

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getAnamnese() {
        return anamnese;
    }

    public void setAnamnese(String anamnese) {
        this.anamnese = anamnese;
    }

    public Planos mudarPlano(Planos plano, Long id) {
        if (this.id == id) {
            return this.plano = plano;
        }
        return this.plano;
    }


}
