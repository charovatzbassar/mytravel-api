package com.example.api.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entity) {
        super(entity.concat(" not found!"));
    }


}
