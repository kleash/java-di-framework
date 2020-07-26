package com.github.kleash.di;

import com.github.kleash.di.exception.UnsatisfiedDependencyException;
import com.github.kleash.di.extras.BeanA;
import com.github.kleash.di.extras.BeanB;
import com.github.kleash.di.extras.ExtraClass;
import com.github.kleash.di.extras.dependencies.BeanPrototype;
import com.github.kleash.di.extras.dependencies.BeanSingleton;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * Unit test for simple ApplicationContextCreator.
 */
public class ApplicationContextCreatorTest {

    private ApplicationContextCreator applicationContextCreator;

    @Before
    public void createInstanceAndScan() {
        applicationContextCreator = ApplicationContextCreator.getInstance();
        applicationContextCreator.scan("com.github.kleash.di.extras");
    }

    @Test
    public void testShouldAnswerWithTrue() {
        assertTrue(true);
    }


    @Test
    public void testGetInstance() {
        assertThat(applicationContextCreator, instanceOf(ApplicationContextCreator.class));
    }

    @Test
    public void testGetBeans() {

        Object beanA = applicationContextCreator.getBean(BeanA.class);
        Object beanB = applicationContextCreator.getBean(BeanB.class);
        Object beanProto = applicationContextCreator.getBean(BeanPrototype.class);
        Object beanSingleton = applicationContextCreator.getBean(BeanSingleton.class);

        assertThat(beanA, instanceOf(BeanA.class));
        assertThat(beanB, instanceOf(BeanB.class));
        assertThat(beanProto, instanceOf(BeanPrototype.class));
        assertThat(beanSingleton, instanceOf(BeanSingleton.class));
    }

    @Test(expected = UnsatisfiedDependencyException.class)
    public void testGetBeansFail() {
        applicationContextCreator.getBean(ExtraClass.class);
    }

    @Test
    public void testSingletonBeans() {
        Object beanA = applicationContextCreator.getBean(BeanA.class);
        Object beanB = applicationContextCreator.getBean(BeanB.class);
        assertEquals(((BeanA) beanA).getSingleton(), (((BeanB) beanB).getSingleton()));
    }

    @Test
    public void testProtoTypeBeans() {
        Object beanA = applicationContextCreator.getBean(BeanA.class);
        Object beanB = applicationContextCreator.getBean(BeanB.class);
        assertNotEquals(((BeanA) beanA).getPrototype(), (((BeanB) beanB).getPrototype()));
    }
}
