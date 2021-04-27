package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.util.AppLogger;
import com.epam.jwd.core_final.util.IAppLogger;

import java.util.Collection;

public interface ApplicationContext {
    IAppLogger logger = AppLogger.getInstance();

    <T extends BaseEntity>Collection<T> retrieveBaseEntityList(Class<T> tClass);

    void init() throws InvalidStateException;

}
