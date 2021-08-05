package com.ede.edecustomerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@SpringBootApplication
@EnableEurekaClient
@EnableScheduling
public class EdeCustomerServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(EdeCustomerServiceApplication.class, args);
	}

}
