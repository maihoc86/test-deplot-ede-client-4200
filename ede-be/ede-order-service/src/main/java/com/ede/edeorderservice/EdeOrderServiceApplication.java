package com.ede.edeorderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EdeOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdeOrderServiceApplication.class, args);
	}

}
