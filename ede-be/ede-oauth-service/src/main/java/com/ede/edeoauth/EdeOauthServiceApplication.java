package com.ede.edeoauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EdeOauthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdeOauthServiceApplication.class, args);
	}

}
