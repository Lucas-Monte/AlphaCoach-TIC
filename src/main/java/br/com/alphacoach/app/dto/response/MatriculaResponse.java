package br.com.alphacoach.app.dto.response;

import br.com.alphacoach.app.model.enums.StatusMatricula;

import java.time.LocalDate;

public record MatriculaResponse(Long id,
                                Long planoId,
                                Long alunoId,
                                StatusMatricula statusMatricula,
                                LocalDate dataInicio) {
}
