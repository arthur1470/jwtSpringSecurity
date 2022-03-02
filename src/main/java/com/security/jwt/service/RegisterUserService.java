package com.security.jwt.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.jwt.model.RoleModel;
import com.security.jwt.model.UserModel;
import com.security.jwt.repository.RoleRepository;
import com.security.jwt.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegisterUserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder passwordEncoder;

	public UserModel save(UserModel userModel) {
		userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
		return this.userRepository.save(userModel);
	}

	public void addRoleToUser(String email, String name) {
		UserModel user = this.userRepository.findByEmail(email).orElseThrow();
		RoleModel role = this.roleRepository.findByName(name).orElseThrow();

		user.getRoles().add(role);

		this.userRepository.save(user);
	}

	public UserModel findUserByEmail(String email) {
		return this.userRepository.findByEmail(email).orElseThrow();
	}

	public UserModel findUserByCpf(String cpf) {
		return this.userRepository.findByEmail(cpf).orElseThrow();
	}
}
