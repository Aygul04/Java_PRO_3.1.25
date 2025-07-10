package com.example.javapro.tests;

import com.example.javapro.annotation.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestRunner {

    public static void runTests(Class<?> testClass) {
        try {
            Method[] allMethods = testClass.getDeclaredMethods();

            TestClassMethods methods = classifyMethods(allMethods);

            validateSuiteAnnotations(methods);

            Object testInstance = testClass.getDeclaredConstructor().newInstance();

            executeTests(testInstance, methods);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static TestClassMethods classifyMethods(Method[] allMethods) {
        TestClassMethods methods = new TestClassMethods();

        for (Method method : allMethods) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                methods.beforeSuite = method;
            } else if (method.isAnnotationPresent(AfterSuite.class)) {
                methods.afterSuite = method;
            } else if (method.isAnnotationPresent(BeforeTest.class)) {
                methods.beforeTestMethods.add(method);
            } else if (method.isAnnotationPresent(AfterTest.class)) {
                methods.afterTestMethods.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                methods.testMethods.add(method);
            }
        }

        // Сортируем тестовые методы по приоритету
        methods.testMethods.sort(Comparator.comparingInt(
                (Method m) -> m.getAnnotation(Test.class).priority()).reversed());

        return methods;
    }

    private static void validateSuiteAnnotations(TestClassMethods methods) {
        if (methods.beforeSuite != null && !Modifier.isStatic(methods.beforeSuite.getModifiers())) {
            throw new RuntimeException("@BeforeSuite method must be static");
        }
        if (methods.afterSuite != null && !Modifier.isStatic(methods.afterSuite.getModifiers())) {
            throw new RuntimeException("@AfterSuite method must be static");
        }
    }

    private static void executeTests(Object testInstance, TestClassMethods methods) throws Exception {
        if (methods.beforeSuite != null) {
            methods.beforeSuite.invoke(null);
        }

        for (Method testMethod : methods.testMethods) {

            for (Method beforeMethod : methods.beforeTestMethods) {
                beforeMethod.invoke(testInstance);
            }

            if (testMethod.isAnnotationPresent(CsvSource.class)) {
                runTestWithCsvSource(testInstance, testMethod);
            } else {
                testMethod.invoke(testInstance);
            }

            for (Method afterMethod : methods.afterTestMethods) {
                afterMethod.invoke(testInstance);
            }
        }

        if (methods.afterSuite != null) {
            methods.afterSuite.invoke(null);
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

    private static class TestClassMethods {
        Method beforeSuite;
        Method afterSuite;
        List<Method> beforeTestMethods = new ArrayList<>();
        List<Method> afterTestMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();
    }
}

