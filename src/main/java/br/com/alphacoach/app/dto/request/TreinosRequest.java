package br.com.alphacoach.app.dto.request;

import br.com.alphacoach.app.model.ExercicioTreino;
import br.com.alphacoach.app.model.Exercicios;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TreinosRequest(@NotEmpty(message = "Nome Obrigatório") String nome,
                             @NotNull(message = "Aluno Id Obrigatório") Long alunoId,
                             @NotNull(message = "Ao menos um exercício é obrigatório") List<ExercicioTreinoRequest> exercicios) {
}
