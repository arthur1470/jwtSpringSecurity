package com.security.jwt.service.user;

import org.springframework.stereotype.Service;

import com.security.jwt.model.UserModel;
import com.security.jwt.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FindUserService {
	
	private UserRepository userRepository;
	
	public UserModel findByEmail(String email) {
		return this.userRepository.findByEmail(email).orElseThrow();
	}

	public UserModel findByCpf(String cpf) {
		return this.userRepository.findByEmail(cpf).orElseThrow();
	}	
}
