package br.com.alphacoach.app.dto.request;

import br.com.alphacoach.app.model.Parcela;
import br.com.alphacoach.app.model.enums.StatusMatricula;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MatriculaRequest(@NotNull(message = "Id é obrigatório") Long id,
                               @NotNull(message = "Id do plano é obrigatório") Long planoId,
                               @NotNull(message = "Id do aluno é obrigatório") Long alunoId,
                               @NotEmpty(message = "O status da matricula é obrigatória") StatusMatricula statusMatricula,
                               @NotEmpty(message = "A data de inicio é obrigatória") LocalDate dataInicio,
                               @NotNull(message = "Ao menos uma parcela é obrigatória") List<ParcelaRequest> parcelas) {
}
