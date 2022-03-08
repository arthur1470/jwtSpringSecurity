package com.security.jwt.service.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.jwt.model.UserModel;
import com.security.jwt.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegisterUserService {

	private UserRepository userRepository;
	private BCryptPasswordEncoder passwordEncoder;

	public UserModel execute(UserModel userModel) {
		userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
		return this.userRepository.save(userModel);
	}
}
