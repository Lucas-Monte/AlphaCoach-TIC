package br.com.alphacoach.app.exception;

public class CheckInAntesDoTempoException extends RuntimeException{
    public CheckInAntesDoTempoException() {
        super("Check-in só pode ser feito com até 30 minutos de antecedencia");
    }
}
