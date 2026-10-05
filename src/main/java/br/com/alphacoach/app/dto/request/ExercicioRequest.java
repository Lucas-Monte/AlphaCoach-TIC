package br.com.alphacoach.app.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record ExercicioRequest(@NotEmpty(message = "Nome obrigatório") String nome,
                               String descricao,
                               String link) {
}
