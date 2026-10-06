package br.com.alphacoach.app.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PagamentoAlunoResponse(Long id,
                                     Long alunoId,
                                     LocalDate dataPagamento,
                                     BigDecimal valorPago) {
}
