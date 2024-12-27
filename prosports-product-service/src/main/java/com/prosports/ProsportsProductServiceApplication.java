package com.prosports;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProsportsProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProsportsProductServiceApplication.class, args);
	}

}
