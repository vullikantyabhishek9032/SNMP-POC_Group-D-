package com.hcl.usermanagement;

import com.hcl.usermanagement.entity.RoleResponsibility;
import com.hcl.usermanagement.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UsermanagementApplication {

	public static void main(String[] args) {

		SpringApplication.run(
				UsermanagementApplication.class,
				args);
        System.out.println("User management service Running ");
	}

	@Bean
	CommandLineRunner loadRoles(
			RoleRepository roleRepository) {

		return args -> {

			System.out.println("Loading Roles...");

			if (roleRepository.count() == 0) {

				roleRepository.save(
						new RoleResponsibility(
								null,
								"ADMIN"));

				roleRepository.save(
						new RoleResponsibility(
								null,
								"DEVELOPER"));

				roleRepository.save(
						new RoleResponsibility(
								null,
								"TESTER"));

				System.out.println(
						"Default Roles Inserted Successfully");
			} else {

				System.out.println(
						"Roles Already Exist");
			}
		};
	}
}