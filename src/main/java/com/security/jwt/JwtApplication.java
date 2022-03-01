package com.security.jwt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.security.jwt.model.RoleModel;
import com.security.jwt.model.UserModel;
import com.security.jwt.repository.RoleRepository;
import com.security.jwt.service.UserService;

@SpringBootApplication
public class JwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
	}
	
	@Bean
	CommandLineRunner runner(RoleRepository roleRepository, UserService userService) {
		return args -> {
			roleRepository.save(new RoleModel("ROLE_MASTER"));
			roleRepository.save(new RoleModel("ROLE_ADMIN"));
			roleRepository.save(new RoleModel("ROLE_USER"));
			
			userService.save(new UserModel("Pedro", "pedro@gmail.com", "123"));
			userService.save(new UserModel("Joao", "joao@gmail.com", "456"));
			userService.save(new UserModel("Ana", "ana@gmail.com", "789"));
			
			userService.addRoleToUser("pedro@gmail.com", "ROLE_MASTER");
			userService.addRoleToUser("joao@gmail.com", "ROLE_ADMIN");
			userService.addRoleToUser("ana@gmail.com", "ROLE_USER");
		};
	}

}
