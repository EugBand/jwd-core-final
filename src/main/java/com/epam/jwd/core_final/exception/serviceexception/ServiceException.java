package com.epam.jwd.core_final.exception.serviceexception;

public abstract class ServiceException extends RuntimeException{
    public ServiceException(String message) {
        super(message);
    }
}
