package br.com.alphacoach.app.dto.response;

import br.com.alphacoach.app.model.enums.UserTypes;

public record RegisterUserResponse(String nome, String email, String cpf, UserTypes tipo) {
}
