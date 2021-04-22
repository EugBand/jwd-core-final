package com.epam.jwd.core_final.factory;

import com.epam.jwd.core_final.domain.CrewMember;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public final class SingletonFactory {
    private static final Map<Class, EntityFactory> instances = new HashMap<>();

    private SingletonFactory() {
        SingletonFactory.getInstance(CrewMember.class);
    }

    public static <T> T getInstance(Class<T> type)  {
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
