package com.example.javapro.tests;

import com.example.javapro.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TestRunner {
    public static void runTests(Class<?> testClass) {
        try {
            validateSuiteAnnotations(testClass);

            Object testInstance = testClass.getDeclaredConstructor().newInstance();

            runBeforeSuite(testClass, testInstance);

            runTestMethods(testClass, testInstance);

            runAfterSuite(testClass, testInstance);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void validateSuiteAnnotations(Class<?> testClass) {
        long beforeSuiteCount = Arrays.stream(testClass.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(BeforeSuite.class))
                .count();

        if (beforeSuiteCount > 1) {
            throw new RuntimeException("More than one @BeforeSuite method");
        }

        long afterSuiteCount = Arrays.stream(testClass.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(AfterSuite.class))
                .count();

        if (afterSuiteCount > 1) {
            throw new RuntimeException("More than one @AfterSuite method");
        }
    }

    private static void runBeforeSuite(Class<?> testClass, Object testInstance) throws Exception {
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new RuntimeException("@BeforeSuite method must be static");
                }
                method.invoke(null);
            }
        }
    }

    private static void runAfterSuite(Class<?> testClass, Object testInstance) throws Exception {
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(AfterSuite.class)) {
                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new RuntimeException("@AfterSuite method must be static");
                }
                method.invoke(null);
            }
        }
    }

    private static void runTestMethods(Class<?> testClass, Object testInstance) throws Exception {
        List<Method> testMethods = Arrays.stream(testClass.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(Test.class))
                .sorted(Comparator.comparingInt((Method m) ->
                        m.getAnnotation(Test.class).priority()).reversed())
                .toList();

        for (Method testMethod : testMethods) {
            runBeforeAfterTest(testClass, testInstance, BeforeTest.class);

            if (testMethod.isAnnotationPresent(CsvSource.class)) {
                runTestWithCsvSource(testInstance, testMethod);
            } else {
                testMethod.invoke(testInstance);
            }

            runBeforeAfterTest(testClass, testInstance, AfterTest.class);
        }
    }

    private static void runBeforeAfterTest(Class<?> testClass, Object testInstance,
                                           Class<? extends Annotation> annotation) throws Exception {
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                method.invoke(testInstance);
            }
        }
    }

    private static void runTestWithCsvSource(Object testInstance, Method testMethod) throws Exception {
        CsvSource csvSource = testMethod.getAnnotation(CsvSource.class);
        String[] params = csvSource.value().split(",\\s*");

        Class<?>[] paramTypes = testMethod.getParameterTypes();
        if (params.length != paramTypes.length) {

            throw new RuntimeException("Number of CSV parameters doesn't match method arguments");
        }

        Object[] args = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            args[i] = convertParam(params[i], paramTypes[i]);
        }

        testMethod.invoke(testInstance, args);
    }

    private static Object convertParam(String param, Class<?> targetType) {
        if (targetType == String.class) {
            return param;
        } else if (targetType == int.class || targetType == Integer.class) {
            return Integer.parseInt(param);
        } else if (targetType == long.class || targetType == Long.class) {
            return Long.parseLong(param);
        } else if (targetType == double.class || targetType == Double.class) {
            return Double.parseDouble(param);
        } else if (targetType == float.class || targetType == Float.class) {
            return Float.parseFloat(param);
        } else if (targetType == boolean.class || targetType == Boolean.class) {
            return Boolean.parseBoolean(param);
        } else {
            throw new RuntimeException("Unsupported parameter type: " + targetType.getName());
        }
    }
}


