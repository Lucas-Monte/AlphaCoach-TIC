package br.com.alphacoach.app.exception;

public class UrlInvalidaException extends BusinessException {
  public UrlInvalidaException(Throwable cause) {
    super("URL com formato inválido", cause);
  }
}
