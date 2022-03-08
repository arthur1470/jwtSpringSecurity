package com.security.jwt.service.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.jwt.model.UserModel;
import com.security.jwt.repository.UserRepository;
import com.security.jwt.security.userdetails.CustomUserDetail;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoginUserService implements UserDetailsService {

	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserModel user = userRepository.findByCpfOrEmail(username).orElseThrow();
		List<SimpleGrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

		return new CustomUserDetail(user.getCpf(), user.getPassword(), authorities, user.getPermissions());
	}
}
