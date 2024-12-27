package com.prosports;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProsportsOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProsportsOrderServiceApplication.class, args);
	}

}