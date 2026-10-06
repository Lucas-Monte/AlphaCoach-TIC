package br.com.alphacoach.app.dto.request;

import br.com.alphacoach.app.model.enums.StatusParcela;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ParcelaRequest(@NotNull(message = "Id da matriula é obrigatória") Long matriculaId) {
}
