package br.com.alphacoach.app.dto.request;

import br.com.alphacoach.app.model.enums.TipoPlano;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PlanosRequest(@NotEmpty(message = "Descrição obrigatória") String descricao,
                            @NotNull(message = "Valor obrigatório") BigDecimal valor,
                            @NotNull(message = "Duração obrigatória") Integer duracaoMeses,
                            @NotEmpty(message = "Tipo de plano obrigatório") TipoPlano tipoPlano) {
}
