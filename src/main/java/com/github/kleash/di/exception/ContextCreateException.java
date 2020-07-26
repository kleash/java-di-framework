package com.github.kleash.di.exception;

/**
 * This exception is thrown, if dependency injector if unable to initiate classloader and scan passed package.
 */
public class ContextCreateException extends RuntimeException {
    public ContextCreateException(String errorMessage) {
        super(errorMessage);
    }

    public ContextCreateException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
