package br.com.alphacoach.app.exception;

public class CheckInDepoisDoTempoException extends RuntimeException{
    public CheckInDepoisDoTempoException() {
        super("Check-in não pode ser feito com 30 minutos de atraso");
    }
}
