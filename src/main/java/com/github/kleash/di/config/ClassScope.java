package com.github.kleash.di.config;

/**
 * Available class scopes
 */
public final class ClassScope {

    /**
     * Only one instance of bean will exist throughout application context
     */
    public static final String SINGLETON = "singleton";

    /**
     * Separate instance for every dependency
     */
    public static final String PROTOTYPE = "prototype";

    private ClassScope() {
    }
}
