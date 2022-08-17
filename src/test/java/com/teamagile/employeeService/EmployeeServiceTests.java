package com.teamagile.employeeService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.ServletComponentScan;

import com.teamagile.employeeService.EmployeeServiceApplication;

@SpringBootTest(classes = EmployeeServiceApplication.class)
@ServletComponentScan
class EmployeeServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
