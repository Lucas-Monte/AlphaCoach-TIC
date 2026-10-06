package br.com.alphacoach.app.dto.request;

import br.com.alphacoach.app.model.enums.StatusParcela;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ParcelaRequest(@NotNull(message = "Id da parcela é obrigatório") Long id,
                             @NotNull(message = "Id da matriula é obrigatória") Long matriculaId,
                             @NotNull(message = "Status da parcela é obrigatório") StatusParcela statusParcela,
                             @NotNull(message = "Vencimento da parcela é obrigatório") @JsonFormat(pattern = "dd/MM/yyyy") LocalDate vencimentoParcela) {
}
