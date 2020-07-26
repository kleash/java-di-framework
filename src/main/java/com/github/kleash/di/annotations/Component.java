package com.github.kleash.di.annotations;

import com.github.kleash.di.config.ClassScope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Marks a class as to be a be automatically scanned and instantiated.
 * <p>
 * If a class is not marked by this annotation, it cannot be autowired in other component marked classes.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {
    /**
     * @return Scope of class, default as {@code @ClassScope.SINGLETON}
     */
    String type() default ClassScope.SINGLETON;
}
