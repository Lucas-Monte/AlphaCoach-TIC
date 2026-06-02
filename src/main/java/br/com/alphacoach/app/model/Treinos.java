package br.com.alphacoach.app.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "treinos")
public class Treinos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 100)
    private String nome;
    @ManyToOne
    @JoinColumn(name = "alunoId", nullable = false)
    private Aluno aluno;
    @Column
    private Date dataCriacao;
    @Column
    private boolean status;
    @OneToMany(mappedBy = "treino", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExercicioTreino> exercicios;

    public Treinos(long id, String nome, Aluno aluno, Date dataCriacao, List<ExercicioTreino> exercicios) {
        this.id = id;
        this.nome = nome;
        this.aluno = aluno;
        this.dataCriacao = dataCriacao;
        this.status = false;
        this.exercicios = exercicios;
    }

    public Treinos() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ExercicioTreino> getExercicios() {
        return exercicios;
    }

    public void setExercicios(List<ExercicioTreino> exercicios) {
        this.exercicios = exercicios;
    }

    public void adicionarExercicio(ExercicioTreino exercicio) {
        this.exercicios.add(exercicio);
    }

    public void removerExercicio(ExercicioTreino exercicio) {
        this.exercicios.removeIf(procurado -> procurado.equals(exercicio));
    }

    public int duracaoEstimada() {
        int resultado;
        int descansoTotal = 0;
        int seriesTotal = 0;
        int repeticoesTotal = 0;
        for (ExercicioTreino exercicio : exercicios) {
            descansoTotal += exercicio.getTempoDescanso();
            seriesTotal += exercicio.getSeries();
            repeticoesTotal += exercicio.getRepeticoes();
        }

        resultado = (seriesTotal * repeticoesTotal) + descansoTotal;
        return resultado;
    }
}
