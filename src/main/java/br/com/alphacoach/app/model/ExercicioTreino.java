package br.com.alphacoach.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "exercicioTreino")
public class ExercicioTreino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "treinoId", nullable = false)
    private Treinos treino;
    @ManyToOne
    @JoinColumn(name = "exercicioId", nullable = false)
    private Exercicios exercicio;
    @Column
    private float potencia;
    @Column
    private float intensidade;
    @Column
    private int series;
    @Column
    private int repeticoes;
    @Column(length = 20)
    private String carga;
    @Column
    private int tempoDescanso;
    @Column
    private boolean status;

    public ExercicioTreino(long id, Treinos treino, Exercicios exercicio, float potencia, float intensidade, int series, int repeticoes, String carga, int tempoDescanso) {
        this.id = id;
        this.treino = treino;
        this.exercicio = exercicio;
        this.potencia = potencia;
        this.intensidade = intensidade;
        this.series = series;
        this.repeticoes = repeticoes;
        this.carga = carga;
        this.tempoDescanso = tempoDescanso;
        this.status = false;
    }

    public ExercicioTreino() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Treinos getTreino() {
        return treino;
    }

    public void setTreino(Treinos treino) {
        this.treino = treino;
    }

    public Exercicios getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicios exercicio) {
        this.exercicio = exercicio;
    }

    public float getPotencia() {
        return potencia;
    }

    public void setPotencia(float potencia) {
        this.potencia = potencia;
    }

    public float getIntensidade() {
        return intensidade;
    }

    public void setIntensidade(float intensidade) {
        this.intensidade = intensidade;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(int repeticoes) {
        this.repeticoes = repeticoes;
    }

    public String getCarga() {
        return carga;
    }

    public void setCarga(String carga) {
        this.carga = carga;
    }

    public int getTempoDescanso() {
        return tempoDescanso;
    }

    public void setTempoDescanso(int tempoDescanso) {
        this.tempoDescanso = tempoDescanso;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void concluirExercicio() {
        this.status = true;
    }
}
