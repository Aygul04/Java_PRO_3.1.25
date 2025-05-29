package com.example.javapro;

import com.example.javapro.tests.TestClass;
import com.example.javapro.tests.TestRunner;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JavaProApplicationTests {

	@Test
	void contextLoads() {
		TestRunner.runTests(TestClass.class);
	}

}
