package com.security.jwt.resource;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.jwt.model.UserModel;
import com.security.jwt.repository.UserRepository;
import com.security.jwt.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserResource {

	private UserService userService;
	private UserRepository userRepository;
	
	@GetMapping
	public List<UserModel> findAll() {
		return this.userRepository.findAll();
	}
	
	@PostMapping
	public UserModel save(@RequestBody UserModel userModel) {
		return this.userService.save(userModel);
	}
}
