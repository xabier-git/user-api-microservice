package com.xabier.desafio.exception;

public class BussinesException extends Exception {

    private static final long serialVersionUID = 1L;

    public BussinesException(String message) {
        super(message);
    }

    public BussinesException(String message, Throwable cause) {
        super(message, cause);
    }

    public BussinesException(Throwable cause) {
        super(cause);
    }       

}
