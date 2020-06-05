package br.com.btg.jokenpo.services.exceptions;

import br.com.btg.jokenpo.enumeration.EnumException;

public class GenericException extends RuntimeException{

    public GenericException(String message){
        super(message);
    }

    public GenericException(String message, Throwable cause){
        super(message, cause);
    }

    public GenericException(EnumException enumGenericException) {
    }
}
