package com.security.jwt.resource;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.jwt.model.UserModel;
import com.security.jwt.repository.UserRepository;
import com.security.jwt.service.user.RegisterUserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserResource {

	private RegisterUserService registerUser;
	private UserRepository repository;

	@GetMapping
	public List<UserModel> findAll() {
		return this.repository.findAll();
	}

	@PostMapping
	public UserModel save(@RequestBody UserModel userModel) {
		return this.registerUser.execute(userModel);
	}
}
