package br.com.btg.jokenpo.services.exceptions;

public class ContentAlreadyExistsException extends RuntimeException{

    public ContentAlreadyExistsException(String message) {
        super(message);
    }

    public ContentAlreadyExistsException(String message, Throwable cause ){
        super(message, cause);
    }
}
