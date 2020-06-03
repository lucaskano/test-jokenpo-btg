package br.com.btg.jokenpo.exception;

import br.com.btg.jokenpo.enumeration.EnumException;

public class JokenpoException extends Exception{

    public JokenpoException(EnumException enumException){
        super(enumException.getCode() + " - " + enumException.getMessage());
    }

    public JokenpoException(String errorMessage){
        super(errorMessage);
    }
}
