package com.ede.edegateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EdeGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdeGatewayApplication.class, args);
	}

}
