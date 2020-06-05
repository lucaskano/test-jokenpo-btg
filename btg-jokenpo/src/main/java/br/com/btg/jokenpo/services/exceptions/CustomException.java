package br.com.btg.jokenpo.services.exceptions;

public class CustomException extends RuntimeException{

    private String errorType;

    public CustomException(String message, String errorType){
        super(message);
    }

    public CustomException(String message, Throwable cause){
        super(message, cause);
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }
}
