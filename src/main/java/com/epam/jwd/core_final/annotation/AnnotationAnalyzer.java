package com.epam.jwd.core_final.annotation;

import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
public class AnnotationAnalyzer {
    private static final Map<Class<?>, Object> bucket = new HashMap<>();
    private static Reflections reflections = new Reflections("com.epam.jwd.core_final");
    private static Set<Class<?>> set = reflections.getTypesAnnotatedWith(ISingleton.class);


    public static void init(){
        set.forEach(item-> System.out.println(item.getSimpleName()));
    }



//    ISingleton getInstance(Class type) {
//        for (Class clazz : instances.keySet()) {
//            if (clazz.getSimpleName().equals(type.getSimpleName())) {
//                return instances.get(clazz);
//            }
//        }
//        ISingleton newInstance = null;
//        try {
//            newInstance = (ISingleton) type.getDeclaredConstructor().newInstance();
//        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        instances.put(type, newInstance);
//        return newInstance;
//    }
}
