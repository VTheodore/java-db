package com.vezenkov.restdemo.exception;

public class InvalidEntityException extends RuntimeException{
    public InvalidEntityException() {
        super();
    }

    public InvalidEntityException(String message) {
        super(message);
    }

    public InvalidEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEntityException(Throwable cause) {
        super(cause);
    }
}
