package com.teamagile.bfEmployeeApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BfEmployeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BfEmployeeApplication.class, args);
	}

}
