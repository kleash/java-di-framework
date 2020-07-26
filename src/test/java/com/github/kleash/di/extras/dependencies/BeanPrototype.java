package com.github.kleash.di.extras.dependencies;

import com.github.kleash.di.annotations.Component;
import com.github.kleash.di.config.ClassScope;

@Component(type = ClassScope.PROTOTYPE)
public class BeanPrototype {
    private String beanName = "BeanPrototype";

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
