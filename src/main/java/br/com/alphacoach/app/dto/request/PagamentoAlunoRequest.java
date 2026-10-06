package br.com.alphacoach.app.dto.request;

import br.com.alphacoach.app.model.enums.FormaPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PagamentoAlunoRequest(@NotNull(message = "Id do aluno é obrigatório") Long alunoId,
                                    @NotNull(message = "Data de pagamento é obrigatória") @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dataPagamento,
                                    @NotNull(message = "Valor pago é obrigatório") BigDecimal valorPago,
                                    @NotNull(message = "Forma de pagamento é obrigatória") FormaPagamento formaPagamento,
                                    @NotNull(message = "Id da parcela é obrigatório") Long parcelaId) {
}
