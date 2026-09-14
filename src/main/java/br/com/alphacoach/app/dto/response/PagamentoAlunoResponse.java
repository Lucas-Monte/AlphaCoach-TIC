package br.com.alphacoach.app.dto.response;

import java.time.LocalDate;
//Mexer quando a relação StatusPagamento e PagamentoAluno estiver pronto
public record PagamentoAlunoResponse(Long id,
                                     Long aluniId,
                                     LocalDate competencia,
                                     LocalDate dataPagamento,
                                     Float valorPago,
                                     Long planoId) {
}
