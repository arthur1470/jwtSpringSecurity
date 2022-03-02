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
import com.security.jwt.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoginUserService implements UserDetailsService {

	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserModel user = findUserByUserName(username);
		List<SimpleGrantedAuthority> authorities = getUserAuthorities(user);

		return new User(user.getEmail(), user.getPassword(), authorities);
	}

	private UserModel findUserByUserName(String username) {
		if (username.contains("@")) {
			return this.userRepository.findByEmail(username).orElseThrow();
		} else {
			return this.userRepository.findByEmail(username).orElseThrow();
		}
	}

	private List<SimpleGrantedAuthority> getUserAuthorities(UserModel user) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		for (RoleModel role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		
		return authorities;
	}
}
