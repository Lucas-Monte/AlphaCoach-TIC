package br.com.alphacoach.app.dto.response;

public record ExercicioResponse(Long id, String nome, String descricao, String link, Boolean status) {
}
