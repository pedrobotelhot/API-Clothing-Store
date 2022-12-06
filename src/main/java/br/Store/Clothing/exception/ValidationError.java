package br.Store.Clothing.exception;

public class ValidationError {
    private final String field;
    private final String message;

    ValidationError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
