package com.epam.jwd.core_final.annotation;

import com.epam.jwd.core_final.factory.SingletonFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class AnnotationAnalyzer {
//    Reflections reflections = new Reflections("edu.pqdn.scanner");
//    Set<Class<?>> set = reflections.getTypesAnnotatedWith(MyAnnotation.class);
//
//    List<String> collect = set.stream()
//            .map(Class::getCanonicalName)
//            .collect(Collectors.toList());
//    private static final Map<Class<? extends SingletonFactory>, SingletonFactory> instances = new HashMap<>();
//    SingletonFactory getInstance(Class type)  {
//        for (Class clazz : instances.keySet()) {
//            if (clazz.getSimpleName().equals(type.getSimpleName())) {
//                return instances.get(clazz);
//            }
//        }
//        SingletonFactory newInstance = null;
//        try {
//            newInstance = (SingletonFactory) type.getDeclaredConstructor().newInstance();
//        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        instances.put(type, newInstance);
//        return newInstance;
//    }
}
