package br.com.alphacoach.app.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AgendaTreinoResponse(Long id,
                                   Long alunoId,
                                   LocalDateTime dataEHorario,
                                   Boolean checkin) {
}
