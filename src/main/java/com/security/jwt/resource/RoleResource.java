package com.security.jwt.resource;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.jwt.model.RoleModel;
import com.security.jwt.repository.RoleRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/roles")
public class RoleResource {

	private RoleRepository repository;
	
	@GetMapping
	public List<RoleModel> findAll() {
		return this.repository.findAll();
	}
	
	@PostMapping
	public RoleModel save(@RequestBody RoleModel roleModel) {
		return this.repository.save(roleModel);
	}
}
