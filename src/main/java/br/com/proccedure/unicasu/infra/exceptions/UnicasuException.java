package br.com.proccedure.unicasu.infra.exceptions;

public class UnicasuException extends RuntimeException {

    public UnicasuException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnicasuException(String message) {
        super(message);
    }

}
