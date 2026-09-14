package br.com.alphacoach.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Entity
@Getter
@Setter
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
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;
    @Column (length = 500)
    private String endereco;
    @Column (nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private AlunosTypes tipoAluno;
    @Column
    private Boolean ativo;
    @Column
    private String telefone;
    @ManyToOne
    @JoinColumn(name = "planoId")
    @JsonIgnoreProperties
    private Planos plano;
    @Column (length = 100)
    private String objetivo;
    @Column (length = 500)
    private String anamnese;
    @OneToMany(mappedBy = "aluno")
    @JsonIgnoreProperties("aluno")
    private List<AgendaTreino> agenda;

    public Aluno(Long id, String nome, String email, String cpf, LocalDate dataNascimento, String endereco, AlunosTypes tipoCliente, Boolean ativo, String telefone, Planos plano, String objetivo, String anamnese) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.tipoAluno = tipoCliente;
        this.ativo = ativo;
        this.telefone = telefone;
        this.plano = plano;
        this.objetivo = objetivo;
        this.anamnese = anamnese;
    }

    public Aluno() {
    }


}
