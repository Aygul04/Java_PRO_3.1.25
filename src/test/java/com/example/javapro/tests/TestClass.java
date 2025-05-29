package com.example.javapro.tests;

import com.example.javapro.annotation.*;

public class TestClass {

    @BeforeSuite
    public static void beforeSuite() {
        System.out.println("BeforeSuite method");
    }

    @AfterSuite
    public static void afterSuite() {
        System.out.println("AfterSuite method");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("BeforeTest method");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("AfterTest method");
    }

    @Test(priority = 1)
    public void test1() {
        System.out.println("Test 1 with priority 1");
    }

    @Test(priority = 3)
    public void test2() {
        System.out.println("Test 2 with priority 3");
    }

    @Test
    public void test3() {
        System.out.println("Test 3 with default priority");
    }

    @Test(priority = 10)
    @CsvSource("10, Java, 20, true")
    public void testWithParams(int a, String b, int c, boolean d) {
        System.out.printf("Test with params: %d, %s, %d, %b%n", a, b, c, d);
    }
}