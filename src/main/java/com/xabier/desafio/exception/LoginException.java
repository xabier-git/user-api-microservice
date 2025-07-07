package com.xabier.desafio.exception;

import org.springframework.http.HttpStatus;

public class LoginException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;

    private HttpStatus statusCode = HttpStatus.UNAUTHORIZED; // Default status for login errors


    public LoginException(String message, HttpStatus statusCode) {
        super(message);
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

}
