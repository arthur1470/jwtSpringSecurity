package com.security.jwt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.jwt.model.RoleModel;
import com.security.jwt.model.UserModel;
import com.security.jwt.repository.RoleRepository;
import com.security.jwt.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService{

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

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserModel user = this.userRepository.findByEmail(email).orElseThrow();
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		for (RoleModel role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
				
		return new User(user.getEmail(), user.getPassword(), authorities);
	}
}
