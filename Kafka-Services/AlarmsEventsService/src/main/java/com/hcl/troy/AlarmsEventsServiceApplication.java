package com.hcl.troy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AlarmsEventsServiceApplication {   

	public static void main(String[] args) {
		SpringApplication.run(AlarmsEventsServiceApplication.class, args);
		System.out.println("AlarmEvents Monitoring Service");
	}

}