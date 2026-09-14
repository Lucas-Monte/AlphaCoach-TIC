package br.com.alphacoach.app.model;

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
        this.exercicios.add(exercicio);
    }

    public void removerExercicio(ExercicioTreino exercicio) {
        this.exercicios.removeIf(procurado -> procurado.equals(exercicio));
    }

//    public int duracaoEstimada() {
//        int resultado;
//        int descansoTotal = 0;
//        int seriesTotal = 0;
//        int repeticoesTotal = 0;
//        for (ExercicioTreino exercicio : exercicios) {
//            descansoTotal += exercicio.getTempoDescanso();
//            seriesTotal += exercicio.getSeries();
//            repeticoesTotal += exercicio.getRepeticoes();
//        }
//
//        resultado = (seriesTotal * repeticoesTotal) + descansoTotal;
//        return resultado;
//    }
}
