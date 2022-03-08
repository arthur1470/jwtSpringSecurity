package com.security.jwt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.security.jwt.model.PermissionModel;
import com.security.jwt.model.RoleModel;
import com.security.jwt.model.UserModel;
import com.security.jwt.repository.PermissionRepository;
import com.security.jwt.repository.RoleRepository;
import com.security.jwt.service.permission.AddPermisionToUserService;
import com.security.jwt.service.role.AddRoleToUserService;
import com.security.jwt.service.user.RegisterUserService;

@SpringBootApplication
public class JwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CommandLineRunner runner(RoleRepository roleRepository, 
							 PermissionRepository permissionRepository, 
							 RegisterUserService registerUser, 
							 AddRoleToUserService addRoleService, 
							 AddPermisionToUserService addPermisionService) {
		
		return args -> {
			roleRepository.save(new RoleModel("ROLE_MASTER"));
			roleRepository.save(new RoleModel("ROLE_ADMIN"));
			roleRepository.save(new RoleModel("ROLE_USER"));
			
			permissionRepository.save(new PermissionModel("RELATORIO_MASTER"));
			permissionRepository.save(new PermissionModel("RELATORIO_ADMIN"));
			permissionRepository.save(new PermissionModel("RELATORIO_USER"));
			
			registerUser.execute(new UserModel("Pedro", "pedro@gmail.com", "99999999900", "123"));
			registerUser.execute(new UserModel("Joao", "joao@gmail.com", "99999999911", "456"));
			registerUser.execute(new UserModel("Ana", "ana@gmail.com", "99999999922", "789"));
			
			addRoleService.execute("pedro@gmail.com", "ROLE_MASTER");
			addRoleService.execute("joao@gmail.com", "ROLE_ADMIN");
			addRoleService.execute("ana@gmail.com", "ROLE_USER");

			addPermisionService.execute("pedro@gmail.com", "RELATORIO_MASTER");
			addPermisionService.execute("joao@gmail.com", "RELATORIO_ADMIN");
			addPermisionService.execute("ana@gmail.com", "RELATORIO_USER");
		};
	}

}
