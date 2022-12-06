package br.Store.Clothing.exception;

import br.Store.Clothing.commons.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Value("${enable.trace:#{false}}")
    private boolean enableTrace;

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleItemNotFoundException(
            ItemNotFoundException exception,
            WebRequest request
    ){

        return buildErrorResponse(exception, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException ex,
            WebRequest request
    ){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation error. Check 'errors' field for details.");
        for(ConstraintViolation e: ex.getConstraintViolations()){
            errorResponse.addValidationError(e.getPropertyPath().toString(), ex.getMessage());
        }
        return ResponseEntity.badRequest().body(errorResponse);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error. Check 'errors' field for details.");
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }


    private ResponseEntity<Object> buildErrorResponse(Exception exception,
                                                      HttpStatus httpStatus,
                                                      WebRequest request) {
        return buildErrorResponse(exception, exception.getMessage(), httpStatus, request);
    }

    private ResponseEntity<Object> buildErrorResponse(
            Exception exception,
            String message,
            HttpStatus httpStatus,
            WebRequest request
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
                httpStatus.value(),
                exception.getMessage()
        );

        if(enableTrace) errorResponse.setStackTrace(Utils.getStackTrace(exception));
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }


}
