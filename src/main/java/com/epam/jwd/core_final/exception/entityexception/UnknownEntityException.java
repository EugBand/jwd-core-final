package com.epam.jwd.core_final.exception.entityexception;

import java.util.Arrays;

@SuppressWarnings("unused")
public class UnknownEntityException extends EntityException {

    private final String entityName;
    private final Object[] args;

    public UnknownEntityException(String entityName) {
        super(entityName);
        this.entityName = entityName;
        this.args = null;
    }

    public UnknownEntityException(String entityName, Object[] args) {
        super(entityName);
        this.entityName = entityName;
        this.args = args;
    }

    @Override
    public String getMessage() {
        // todo
        // you should use entityName, args (if necessary)
        return String.format("Unknown entity %s with arguments %s", entityName, Arrays.toString(args));
    }
}
