package br.com.alphacoach.app.dto.response;

import br.com.alphacoach.app.model.UserTypes;

public record RegisterUserResponse(String nome, String email, String cpf, UserTypes tipo) {
}
