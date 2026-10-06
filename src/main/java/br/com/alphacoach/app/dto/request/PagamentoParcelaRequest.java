package br.com.alphacoach.app.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PagamentoParcelaRequest(@NotNull(message = "Id da parcela é obrigatória") Long parcelaId,
                                      @NotNull(message = "Id do pagamento é obrigatório") Long pagamentoId,
                                      @NotNull(message = "Valor aplicado é obrigatório")BigDecimal valorAplicado) {
}
