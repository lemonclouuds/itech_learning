package com.itech.learning.exception;

public class EntityAlreadyExistsException extends RuntimeException{
    public EntityAlreadyExistsException() {
    }

    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
