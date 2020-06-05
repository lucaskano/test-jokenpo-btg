package br.com.btg.jokenpo.controller.exceptions;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{

    private final List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Timestamp timestamp, Integer status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addError(String fieldName, String message){
        errors.add(new FieldMessage(fieldName, message));
    }
}
