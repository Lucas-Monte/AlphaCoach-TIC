package br.com.alphacoach.app.dto.request;

import jakarta.validation.constraints.NotNull;

public record ExercicioTreinoRequest(@NotNull(message = "Exercicio Id é obrigatório") Long exercicioId,
                                     Float potencia,
                                     Float intensidade,
                                     @NotNull(message = "Séries é obrigatório") Integer series,
                                     @NotNull(message = "Repetições é obrigatório") Integer repeticoes,
                                     @NotNull(message = "Carga Id é obrigatório") String carga,
                                     Integer tempoDescanso) {
}
