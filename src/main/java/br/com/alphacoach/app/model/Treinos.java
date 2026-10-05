package br.com.alphacoach.app.model;

import br.com.alphacoach.app.exception.BusinessException;
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
@Table(name = "treinos")
public class Treinos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String nome;
    @ManyToOne
    @JoinColumn(name = "alunoId", nullable = false)
    private Aluno aluno;
    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCriacao;
    @Column
    private Boolean status;
    @OneToMany(mappedBy = "treino", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("treino")
    private List<ExercicioTreino> exercicios = new ArrayList<>();

    public Treinos(Long id, String nome, Aluno aluno, LocalDate dataCriacao, List<ExercicioTreino> exercicios) {
        this.id = id;
        this.nome = nome;
        this.aluno = aluno;
        this.dataCriacao = dataCriacao;
        this.status = false;
        this.exercicios = exercicios;
    }

    public Treinos() {
    }


    public void adicionarExercicio(ExercicioTreino exercicio) {
        exercicio.setTreino(this);
        this.exercicios.add(exercicio);
    }

    public void removerExercicio(ExercicioTreino exercicio) {
        if (this.exercicios.remove(exercicio)) {
            exercicio.setTreino(null);
        }
    }

    public void ativar() {
        if (this.status) {
            throw new BusinessException("Treino ja etá ativo");
        }
        this.status = true;
    }

    public void desativar() {
        if (!this.status) {
            throw new BusinessException("Treino ja etá desativado");
        }
        this.status = false;
    }


 //adicionarExercicio/removerExercicio: sincronizando os dois lados, como nas outras raízes.
    //ativar(), finalizar()/arquivar(): controlam status. Um Boolean pode ser pouco se houver rascunho, ativo e encerrado.
    //concluido(): verifica se todos os exercícios estão concluídos -> por enquanto não.
    //reiniciar(): marca todos como não concluídos para o próximo ciclo -> por enquanto não.

}
