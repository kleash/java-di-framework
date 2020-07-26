package com.github.kleash.di.exception;

/**
 * This exception is thrown, when a class is {@code @Autowired} but not declared as {@code @Component}.
 */
public class UnsatisfiedDependencyException extends RuntimeException {
    public UnsatisfiedDependencyException(String errorMessage) {
        super(errorMessage);
    }

    public UnsatisfiedDependencyException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
