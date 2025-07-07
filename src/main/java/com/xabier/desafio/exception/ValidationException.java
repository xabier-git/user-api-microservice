package com.xabier.desafio.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private HttpStatus status = HttpStatus.BAD_REQUEST;  

    public ValidationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR; // Default status
    }

    public ValidationException(Throwable cause) {
        super(cause);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR; // Default status
    }       

    public HttpStatus getStatus() {
        return status;
    }

}
