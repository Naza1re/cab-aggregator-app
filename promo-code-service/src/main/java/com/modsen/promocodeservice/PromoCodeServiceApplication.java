package com.modsen.promocodeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PromoCodeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PromoCodeServiceApplication.class, args);
	}

}
