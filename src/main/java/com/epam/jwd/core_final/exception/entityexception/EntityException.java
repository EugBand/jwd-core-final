package com.epam.jwd.core_final.exception.entityexception;

public abstract class EntityException extends Exception{
    EntityException(String message) {
        super(message);
    }
}
