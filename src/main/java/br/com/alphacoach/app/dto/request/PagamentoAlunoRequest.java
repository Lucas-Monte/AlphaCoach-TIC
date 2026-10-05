package br.com.alphacoach.app.dto.request;

import br.com.alphacoach.app.model.enums.FormaPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


//Mexer quando a relação StatusPagamento e PagamentoAluno estiver pronto
public record PagamentoAlunoRequest(@NotNull Long alunoId,
                                    @NotNull @JsonFormat(pattern = "MM/yyyy") LocalDate competencia,
                                    @NotNull @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dataPagamento,
                                    @NotNull Float valorPago,
                                    @NotNull FormaPagamento formaPagamento) {
}
