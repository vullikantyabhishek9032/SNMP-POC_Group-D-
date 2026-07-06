package com.example.snmpmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SnmpMicroservice1Application {

	public static void main(String[] args) {
		SpringApplication.run(SnmpMicroservice1Application.class, args);
	}

}
