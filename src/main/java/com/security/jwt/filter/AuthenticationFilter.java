 package com.security.jwt.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.jwt.security.userdetails.CustomUserDetail;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		return this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		CustomUserDetail user = (CustomUserDetail) authResult.getPrincipal();
		
		Algorithm algorithm = Algorithm.HMAC256("ChaveMegaUltraSecreta");
		
		String accessToken = JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
				.withIssuer(request.getRequestURL().toString())
				.withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.withClaim("permissions", user.getPermissions().stream().map(permission -> permission.getName()).collect(Collectors.toList()))
				.sign(algorithm);
		
		String refreshToken = JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
				.withIssuer(request.getRequestURL().toString())
				.sign(algorithm);
		
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		Map<String, String> tokens = new HashMap<>();
		tokens.put("accessToken", accessToken);
		tokens.put("refreshToken", refreshToken);
		
		new ObjectMapper().writeValue(response.getOutputStream(), tokens);
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		
		Map<String, Object> errorInfo = new HashMap<>();
			errorInfo.put("status", httpStatus.value());
			errorInfo.put("message", failed.getMessage());
			errorInfo.put("path", request.getServletPath());
			errorInfo.put("error", httpStatus.getReasonPhrase());
		
		response.setStatus(httpStatus.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		new ObjectMapper().writeValue(response.getOutputStream(), errorInfo);
	}
}
