package com.security.jwt.service.permission;

import org.springframework.stereotype.Service;

import com.security.jwt.model.PermissionModel;
import com.security.jwt.model.UserModel;
import com.security.jwt.repository.PermissionRepository;
import com.security.jwt.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AddPermisionToUserService {

	private UserRepository userRepository;
	private PermissionRepository permissionRepository;
	
	public void execute(String username, String permissionName) {
		UserModel user = userRepository.findByCpfOrEmail(username).orElseThrow();
		PermissionModel permission = permissionRepository.findByName(permissionName).orElseThrow();
		
		user.getPermissions().add(permission);
		
		userRepository.save(user);
	}
}
