package com.ede.edefileservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EdeFileServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdeFileServiceApplication.class, args);
	}

}
