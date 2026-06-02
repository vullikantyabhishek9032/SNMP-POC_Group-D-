package com.hcl.troy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlarmsEventsServiceApplication {   

	public static void main(String[] args) {
		SpringApplication.run(AlarmsEventsServiceApplication.class, args);
		System.out.println("New DB Connection Service");
	}

}