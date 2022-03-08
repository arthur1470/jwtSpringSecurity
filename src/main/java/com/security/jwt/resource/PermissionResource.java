package com.security.jwt.resource;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.jwt.model.PermissionModel;
import com.security.jwt.repository.PermissionRepository;

@RestController
@RequestMapping("/permission")
public class PermissionResource {

	private PermissionRepository repository;
	
	@GetMapping
	public List<PermissionModel> findAll() {
		return this.repository.findAll();
	}
	
	@PostMapping
	public PermissionModel save(@RequestBody PermissionModel permissionModel) {
		return this.repository.save(permissionModel);
	}
}
