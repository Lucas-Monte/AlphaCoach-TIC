package br.com.alphacoach.app.exception;

import br.com.alphacoach.app.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CheckInException.class)
    public ResponseEntity<ErrorResponse> handleCheckIn(CheckInException ex, HttpServletRequest request){
        ErrorResponse body = new ErrorResponse(
                LocalDateTime.now(), 422, ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(body);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> hancleBusiness(BusinessException ex, HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String messagem, HttpServletRequest request) {
        ErrorResponse body = new ErrorResponse(
                LocalDateTime.now(), status.value(), messagem, request.getRequestURI());
        return ResponseEntity.status(status).body(body);
    }
}
