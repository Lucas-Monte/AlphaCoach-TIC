package br.com.alphacoach.app.exception;

public class CheckInDuplicadoException extends CheckInException{
    public CheckInDuplicadoException() {
        super("Check-in já realizado");
    }
}
