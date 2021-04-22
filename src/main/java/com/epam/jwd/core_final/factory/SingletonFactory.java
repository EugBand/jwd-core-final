package com.epam.jwd.core_final.factory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public abstract class SingletonFactory {
    private static final Map<Class<? extends SingletonFactory>, SingletonFactory> instances = new HashMap<>();

    protected SingletonFactory() {
    }

    public static SingletonFactory getInstance(Class<? extends SingletonFactory> type)  {
        for (Class<? extends SingletonFactory> clazz : instances.keySet()) {
            if (clazz.getSimpleName().equals(type.getSimpleName())) {
                return instances.get(clazz);
            }
        }
        SingletonFactory newInstance = null;
        try {
            newInstance = type.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        instances.put(type, newInstance);
        return newInstance;
    }
}
