package com.github.kleash.di.util;

import com.github.kleash.di.annotations.Component;
import com.github.kleash.di.exception.ContextCreateException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Contains method to scan classes according to given package, annotations and more.
 */
public class ClassScanner {

    /**
     * @param packageName The package name for base directory to scan
     * @return All classes in directory
     */
    public static List<Class<?>> getAllComponentClassesInPackage(String packageName) {

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            assert classLoader != null;
            String path = packageName.replace('.', '/');
            Enumeration<URL> resources = classLoader.getResources(path);

            List<File> dirs = new ArrayList<>();
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                dirs.add(new File(resource.getFile()));
            }
            List<Class<?>> classes = new ArrayList<>();
            for (File directory : dirs) {
                classes.addAll(findComponentClasses(directory, packageName));
            }
            return classes;
        } catch (IOException exception) {
            throw new ContextCreateException("IOException while trying to get all resources for " + packageName, exception);
        }
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ContextCreateException is thrown if classes are not found
     */
    private static List<Class<?>> findComponentClasses(File directory, String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();

        if (files == null) return classes;

        for (File file : files) {
            //If directory, recursively call this function to scan subdirs
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findComponentClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                try {
                    Class<?> resolvedClass = Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6));

                    //Check if component annotation is present
                    if (resolvedClass.isAnnotationPresent(Component.class)) {
                        classes.add(resolvedClass);
                    }
                } catch (ClassNotFoundException exception) {
                    throw new ContextCreateException("ClassNotFoundException while trying to get all resources for " + packageName + "." + file, exception);
                }
            }
        }
        return classes;
    }
}
