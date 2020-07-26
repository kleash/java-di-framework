package com.github.kleash.di.exception;

/**
 * This exception is thrown, if dependency injector is unable to create instance of a class with {@code @Component} annotation. For example, when constructor
 * is not visible.
 */
public class BeanCreationException extends RuntimeException {
    public BeanCreationException(String errorMessage) {
        super(errorMessage);
    }

    public BeanCreationException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
