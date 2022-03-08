package com.security.jwt.service.role;

import org.springframework.stereotype.Service;

import com.security.jwt.model.RoleModel;
import com.security.jwt.model.UserModel;
import com.security.jwt.repository.RoleRepository;
import com.security.jwt.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AddRoleToUserService {

	private RoleRepository roleRepository;
	private UserRepository userRepository;
	
	public void execute(String username, String roleName) {
		UserModel user = this.userRepository.findByCpfOrEmail(username).orElseThrow();
		RoleModel role = this.roleRepository.findByName(roleName).orElseThrow();

		user.getRoles().add(role);

		this.userRepository.save(user);
	}
}
