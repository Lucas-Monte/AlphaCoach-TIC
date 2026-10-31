package br.com.alphacoach.app.dto.request;

import br.com.alphacoach.app.model.enums.StatusMatricula;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record MatriculaRequest(@NotNull(message = "Id do plano é obrigatório") Long planoId,
                               @NotNull(message = "Id do aluno é obrigatório") Long alunoId,
                               @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dataInicio) {
}
