package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.factory.EntityFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public final class FactorySingletonBuilder {
    private static final Map<Class, EntityFactory> instances = new HashMap<>();

    private FactorySingletonBuilder() {
    }

    public static <T> T getInstance(Class<T> type) {
        for (Class clazz : instances.keySet()) {
            if (clazz.getSimpleName().equals(type.getSimpleName())) {
                return (T) instances.get(clazz);
            }
        }
        EntityFactory newInstance = null;
        try {
            newInstance = (EntityFactory) type.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        instances.put(type, newInstance);
        return (T) newInstance;
    }
}
