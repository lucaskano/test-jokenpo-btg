package br.com.btg.jokenpo.service.exceptions;

public class ContentAlreadyExistsException extends RuntimeException{

    public ContentAlreadyExistsException(String message) {
        super(message);
    }

    public ContentAlreadyExistsException(String message, Throwable cause ){
        super(message, cause);
    }
}
