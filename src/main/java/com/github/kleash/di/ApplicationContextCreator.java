package com.github.kleash.di;

import com.github.kleash.di.annotations.Autowired;
import com.github.kleash.di.annotations.Component;
import com.github.kleash.di.config.ClassScope;
import com.github.kleash.di.exception.BeanCreationException;
import com.github.kleash.di.exception.UnsatisfiedDependencyException;
import com.github.kleash.di.util.ClassScanner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base class to scan and auto inject dependencies
 */
public class ApplicationContextCreator {

    private final Map<Class<?>, Object> beans;

    private ApplicationContextCreator() {
        beans = new HashMap<>();
    }

    /**
     * @return singleton instance of ApplicationContextCreator
     */
    public static ApplicationContextCreator getInstance() {
        return ApplicationContextCreatorHelper.applicationContextCreator;
    }

    /**
     * @param classToCheck Class whose instance need to be returned
     * @return Instance of given class or null
     */
    public Object getBean(Class<?> classToCheck) {
        if (beans.containsKey(classToCheck)) {
            Object dependencyBean = beans.get(classToCheck);
            //Create new instance if value is null in bean map as this is a prototype class
            return dependencyBean != null ? dependencyBean : createInstanceOfClasses(classToCheck);
        } else {
            throw new UnsatisfiedDependencyException("Unable to find bean in current application context. Please make sure class is marked by supported annotation");
        }
    }

    /**
     * Scan for all classes in given package name and inject dependencies
     *
     * @param packageName The package name for base directory to scan
     */
    public void scan(String packageName) {
        List<Class<?>> classesInPackage = ClassScanner.getAllComponentClassesInPackage(packageName);
        createInstanceOfAllClasses(classesInPackage);
        injectDependencies();
    }

    /**
     * Iterate through all discovered beans and inject field dependencies
     */
    private void injectDependencies() {
        for (Map.Entry<Class<?>, Object> bean : beans.entrySet()) {
            //Get all fields and check for scopes
            Field[] fields = bean.getKey().getDeclaredFields();
            for (Field field : fields) {
                //First make field accessible
                field.setAccessible(true);
                //Check if it's autowired, and inject dependency accordingly
                if (field.isAnnotationPresent(Autowired.class)) {
                    try {
                        field.set(bean.getValue(), getBean(field.getType()));
                    } catch (IllegalAccessException e) {
                        throw new BeanCreationException("Unable to access field to autowire dependency " + field.getType() + " in class " + bean.getKey(), e);
                    }
                }
            }
        }
    }

    /**
     * Instantiate a class using reflection
     *
     * @param classToInstantiate Class to instantiate
     * @return Object of class
     * @throws BeanCreationException, if unable to instantiate the class
     */
    private Object createInstanceOfClasses(Class<?> classToInstantiate) {
        try {
            Constructor<?> constructor = classToInstantiate.getConstructor();
            constructor.setAccessible(true);
            if (!constructor.isAccessible()) {
                throw new BeanCreationException("Unable to create instance of class " + classToInstantiate.getName() + ". Constructor is not visible");
            }
            return constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException exception) {
            throw new BeanCreationException("Unable to create instance of class " + classToInstantiate.getName(), exception);
        }
    }

    /**
     * Instantiates passed classes and stores in global map according to class scope.
     *
     * @param classes Classes list to be instantiated
     */
    private void createInstanceOfAllClasses(List<Class<?>> classes) {
        for (Class<?> classTemp : classes) {
            if (beans.containsKey(classTemp)) {
                //Already created instance of this class
                continue;
            }
            String classScope = classTemp.getAnnotation(Component.class).type();

            //If singleton, create an instance and store it, otherwise store null
            if (classScope.equals(ClassScope.SINGLETON)) {
                beans.put(classTemp, createInstanceOfClasses(classTemp));
            } else {
                beans.put(classTemp, null);
            }

        }
    }

    //Bill Pugh Singleton pattern for context creator to keep only once instance
    private static class ApplicationContextCreatorHelper {
        private static final ApplicationContextCreator applicationContextCreator = new ApplicationContextCreator();
    }


}
