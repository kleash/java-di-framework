package com.github.kleash.di.extras;

import com.github.kleash.di.annotations.Autowired;
import com.github.kleash.di.extras.dependencies.BeanPrototype;
import com.github.kleash.di.extras.dependencies.BeanSingleton;

/**
 * Extra class to make sure we scans only annotated classes
 */
public class ExtraClass {

    @Autowired
    private BeanPrototype prototype;
    @Autowired
    private BeanSingleton singleton;

    public BeanPrototype getPrototype() {
        return prototype;
    }

    public BeanSingleton getSingleton() {
        return singleton;
    }
}
