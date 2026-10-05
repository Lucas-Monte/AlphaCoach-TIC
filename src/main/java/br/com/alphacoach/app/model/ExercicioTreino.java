package br.com.alphacoach.app.model;

import br.com.alphacoach.app.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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

    /*public void atualizarPrescricao(Integer series, Integer repeticoes, String carga, Integer tempoDescanso) {
        if(series <= 0) {
            throw new BusinessException("A sério não pode ser menor ou igual a zero");
        }
        if (repeticoes <= 0) {
            throw new BusinessException("As repetições não podem ser menores ou igual a zero");
        }
        if (carga.isBlank() || carga == null)  {
            throw new BusinessException("A carga precisa ser preenchida");
        }
        if (tempoDescanso < 0) {
            throw new BusinessException("O tempo de descanso não pode ser menor que zero");
        }
        this.setSeries(series);
        this.setRepeticoes(repeticoes);
        this.setCarga(carga);
        this.setTempoDescanso(tempoDescanso);
    }*/



    //atualizarPrescricao(series, repeticoes, carga, descanso): um único ponto para alterar, com validação de valores positivos.
    //concluir() e reabrir(): em vez de só concluirExercicio(). Bloqueie concluir duas vezes -> por enquanto não.
    //calcularVolume(): séries × repetições. Se carga virar numérica, dá para calcular carga × volume -> por enquanto não..
    //calcularTempoEstimado(): séries × (tempo de execução + descanso). Hoje o descanso está em Integer, então defina a unidade (segundos) e documente -> por enquanto não..
    //status como Boolean: se futuramente houver "pulado" ou "substituído", vale virar enum -> por enquanto não.
}
