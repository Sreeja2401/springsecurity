package com.epam;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.epam.entity.User;
import com.epam.repository.UserRepository;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
public class WebprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebprojectApplication.class, args);
	}

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

	/*
	 * @Bean CommandLineRunner commandLineRunner(UserRepository users,
	 * PasswordEncoder encoder)
	 * 
	 * { return args -> { users.save(new User("ROLE_USER", "sreeja",
	 * encoder.encode("Sreeja@2401"))); users.save(new User("ROLE_USER,ROLE_ADMIN",
	 * "sree", encoder.encode("Sreeja@2401"))); };
	 * 
	 * }
	 */

}
