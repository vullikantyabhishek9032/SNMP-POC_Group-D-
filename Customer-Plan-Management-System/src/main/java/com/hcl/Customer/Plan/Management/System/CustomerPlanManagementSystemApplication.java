package com.hcl.Customer.Plan.Management.System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomerPlanManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerPlanManagementSystemApplication.class, args);
        System.out.println("Customer Plan Management Service Running ");
	}
}