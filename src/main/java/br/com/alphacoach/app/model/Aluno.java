package br.com.alphacoach.app.model;

import br.com.alphacoach.app.model.enums.AlunosTypes;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
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
    @Column (length = 100)
    private String objetivo;
    @Column (length = 500)
    private String anamnese;
    @OneToMany(mappedBy = "aluno")
    @JsonIgnoreProperties("aluno")
    private List<AgendaTreino> agenda = new ArrayList<>();

    public Aluno(Long id, String nome, String email, String cpf, LocalDate dataNascimento, String endereco, AlunosTypes tipoCliente, Boolean ativo, String telefone, String objetivo, String anamnese) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.tipoAluno = tipoCliente;
        this.ativo = ativo;
        this.telefone = telefone;
        this.objetivo = objetivo;
        this.anamnese = anamnese;
    }

    public Aluno() {
    }

    //inativar() e reativar(): mudam ativo e bloqueiam repetição. A regra de "não inativar com matrícula ativa" exige consulta, então fica no service.
    //calcularIdade(): a partir de dataNascimento.
    //isMenorDeIdade(): útil se houver exigência de responsável.
    //adicionarAgendamento(AgendaTreino): só se a agenda for tratada como parte do agregado. Como você vai consultar agenda por data e por aluno, um AgendaTreinoRepository provavelmente é melhor.



    //O que deixar nos services
    //Verificar se o aluno tem matrícula ativa antes de inativar (consulta).
    //Impedir dois agendamentos no mesmo horário para o aluno (consulta).
    //Atribuir um treino a um aluno e checar se o plano permite.
    //Qualquer relatório ou listagem filtrada.
    
}
