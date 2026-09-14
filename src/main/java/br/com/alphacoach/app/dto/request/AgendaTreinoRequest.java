package br.com.alphacoach.app.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendaTreinoRequest(@NotNull(message = "Aluno é obrigatório") Long alunoId,
                                  @NotNull(message = "Data e horário são obrigatórios") @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime dataEHorario) {
}
