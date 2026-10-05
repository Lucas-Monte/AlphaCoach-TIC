package br.com.alphacoach.app.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ErrorResponse(@JsonFormat(pattern = "dd/MM/yyy HH:mm")LocalDateTime timestamp,
                            int status,
                            String message,
                            String path) {
}
