package br.com.alphacoach.app.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public record RegisterUserRequest(@NotEmpty(message = "Nome é obrigatório") String name,
                                  @NotEmpty(message = "email é obrigatório") String email,
                                  @NotEmpty(message = "Senha é obrigatória") String password,
                                  @NotEmpty(message = "CPF é obrgatório") String cpf) {
}
