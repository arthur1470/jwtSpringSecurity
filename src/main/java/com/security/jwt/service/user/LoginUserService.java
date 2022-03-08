package com.security.jwt.service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.jwt.model.RoleModel;
import com.security.jwt.model.UserModel;
import com.security.jwt.repository.UserRepository;
import com.security.jwt.security.userdetails.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoginUserService implements UserDetailsService {

	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserModel user = userRepository.findByCpfOrEmail(username).orElseThrow();
		List<SimpleGrantedAuthority> authorities = getUserAuthorities(user);

		return new User(user.getCpf(), user.getPassword(), authorities, user.getPermissions());
	}

	private List<SimpleGrantedAuthority> getUserAuthorities(UserModel user) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		for (RoleModel role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		
		return authorities;
	}
}
