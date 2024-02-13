package com.example.end2end;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class End2EndApplication {

	public static void main(String[] args) {
		SpringApplication.run(End2EndApplication.class, args);
	}

}
