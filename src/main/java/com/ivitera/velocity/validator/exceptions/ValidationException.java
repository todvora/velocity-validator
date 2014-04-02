package com.ivitera.velocity.validator.exceptions;

public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }
}
