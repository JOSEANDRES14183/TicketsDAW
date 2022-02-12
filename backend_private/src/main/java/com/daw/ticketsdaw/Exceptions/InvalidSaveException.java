package com.daw.ticketsdaw.Exceptions;

public class InvalidSaveException extends RuntimeException {
    public InvalidSaveException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public InvalidSaveException(String errorMessage) {
        super(errorMessage);
    }
}
