package com.daw.ticketsdaw.Exceptions;

public class IncorrectFileExtensionException extends RuntimeException {
    public IncorrectFileExtensionException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public IncorrectFileExtensionException(String errorMessage) {
        super(errorMessage);
    }
}
