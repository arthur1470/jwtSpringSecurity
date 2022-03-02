package com.security.jwt.service;

import org.springframework.stereotype.Service;

import com.security.jwt.model.UserModel;
import com.security.jwt.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BuscarUserService {
	
	private UserRepository userRepository;
	
	public UserModel findUserByEmail(String email) {
		return this.userRepository.findByEmail(email).orElseThrow();
	}

	public UserModel findUserByCpf(String cpf) {
		return this.userRepository.findByEmail(cpf).orElseThrow();
	}
}
