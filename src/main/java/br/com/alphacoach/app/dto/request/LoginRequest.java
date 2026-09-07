package br.com.alphacoach.app.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(@NotEmpty(message = "email é obrigatório") String email,
                           @NotEmpty(message = "senha é obrigatória") String password) {
}
