package com.example.demo;

import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.apache.logging.log4j.ThreadContext.isEmpty;


@SpringBootApplication(scanBasePackages = "com.example.demo")

public class SpringRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRestApiApplication.class, args);

	}
		@Bean
		CommandLineRunner run (UserRepository repo, PasswordEncoder encoder){
			return args -> {

				if (repo.findByUsername("admin").isEmpty()) {
					User user = new User();
					user.setUsername("admin");
					user.setPassword(encoder.encode("12345"));
					user.setRole(Role.ADMIN);
					repo.save(user);
				}
			};
		}
	}


