package com.github.kleash.di.annotations;

import java.lang.annotation.*;

/**
 * Marks a field as to be autowired by dependency injection framework.
 *
 * <h3>Autowired Fields</h3>
 * <p>Fields are injected right after construction of a bean, before any config methods
 * are invoked. Such a config field does not have to be public.</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Autowired {
}
