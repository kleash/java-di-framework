package com.github.kleash.di.extras.dependencies;

import com.github.kleash.di.annotations.Component;

@Component
public class BeanSingleton {
    private String beanName = "BeanSingleton";

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
