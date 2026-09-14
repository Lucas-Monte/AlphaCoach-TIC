package br.com.alphacoach.app.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PlanosRequest(@NotEmpty(message = "Descrição obrigatória") String descricao,
                            @NotNull(message = "Valor obrigatório") Float valor,
                            @NotNull(message = "Duração obrigatória") Integer duracaoMeses,
                            @NotEmpty(message = "Tipo de plano obrigatório") String tipoPlano) {
}
