package com.hcl.Troy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class ConsumerKafkaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerKafkaServiceApplication.class, args);
		System.out.println("consumer");
	}

}
