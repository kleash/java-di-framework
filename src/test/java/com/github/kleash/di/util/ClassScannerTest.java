package com.github.kleash.di.util;

import com.github.kleash.di.extras.BeanA;
import com.github.kleash.di.extras.BeanB;
import com.github.kleash.di.extras.dependencies.BeanPrototype;
import com.github.kleash.di.extras.dependencies.BeanSingleton;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ClassScannerTest {

    /**
     * It it make sure getAllComponentClassesInPackage method can scan package, subdirs and only return component annotated classes
     */
    @Test
    public void testGetAllComponentClassesInPackage() {
        List<Class<?>> expectedClasses = Arrays.asList(BeanA.class, BeanB.class, BeanPrototype.class, BeanSingleton.class);
        List<Class<?>> discoveredClasses = ClassScanner.getAllComponentClassesInPackage("com.github.kleash.di.extras");
        assertTrue(expectedClasses.containsAll(discoveredClasses) && discoveredClasses.containsAll(expectedClasses));
    }
}