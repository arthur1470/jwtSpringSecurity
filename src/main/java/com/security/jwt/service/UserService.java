package com.security.jwt.service;

import org.springframework.stereotype.Service;

import com.security.jwt.model.RoleModel;
import com.security.jwt.model.UserModel;
import com.security.jwt.repository.RoleRepository;
import com.security.jwt.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	
	public UserModel save(UserModel userModel) {
		return this.userRepository.save(userModel);
	}
	
	public void addRoleToUser(String email, String name) {
		UserModel user = this.userRepository.findByEmail(email).orElseThrow();
		RoleModel role = this.roleRepository.findByName(name).orElseThrow();
		
		user.getRoles().add(role);
		
		this.userRepository.save(user);
	}
}
