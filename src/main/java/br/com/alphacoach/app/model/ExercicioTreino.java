package br.com.alphacoach.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "exercicioTreino")
public class ExercicioTreino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "treinoId", nullable = false)
    @JsonIgnoreProperties("exercicios")
    private Treinos treino;
    @ManyToOne
    @JoinColumn(name = "exercicioId", nullable = false)
    private Exercicios exercicio;
    @Column
    private Float potencia;
    @Column
    private Float intensidade;
    @Column
    private Integer series;
    @Column
    private Integer repeticoes;
    @Column(length = 20)
    private String carga;
    @Column
    private Integer tempoDescanso;
    @Column
    private Boolean status;

    public ExercicioTreino(Long id, Treinos treino, Exercicios exercicio, Float potencia, Float intensidade, Integer series, Integer repeticoes, String carga, Integer tempoDescanso) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Float getPotencia() {
        return potencia;
    }

    public void setPotencia(Float potencia) {
        this.potencia = potencia;
    }

    public Float getIntensidade() {
        return intensidade;
    }

    public void setIntensidade(Float intensidade) {
        this.intensidade = intensidade;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public Integer getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(Integer repeticoes) {
        this.repeticoes = repeticoes;
    }

    public String getCarga() {
        return carga;
    }

    public void setCarga(String carga) {
        this.carga = carga;
    }

    public Integer getTempoDescanso() {
        return tempoDescanso;
    }

    public void setTempoDescanso(Integer tempoDescanso) {
        this.tempoDescanso = tempoDescanso;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void concluirExercicio() {
        this.status = true;
    }
}
