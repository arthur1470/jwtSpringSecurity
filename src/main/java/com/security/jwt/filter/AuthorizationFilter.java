package com.security.jwt.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class AuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if(request.getServletPath().equals("/login")) {
			filterChain.doFilter(request, response);
		} else {
			String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
			
			if(!ObjectUtils.isEmpty(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
				try {
					String token = authorizationHeader.substring("Bearer ".length());
					Algorithm algorithm = Algorithm.HMAC256("ChaveMegaUltraSecreta");
					JWTVerifier verifier = JWT.require(algorithm).build();
					DecodedJWT decodedJWT = verifier.verify(token);
					
					String cpf = decodedJWT.getSubject();
					String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
					String[] permissions = decodedJWT.getClaim("permissions").asArray(String.class);
					
					List<SimpleGrantedAuthority> authorities = new ArrayList<>();

					UserOn.cpf = cpf; 
					UserOn.permissions = permissions; 
					UserOn.roles = roles;
					
					Arrays.stream(roles).forEach(role -> {
						authorities.add(new SimpleGrantedAuthority(role));
					});
					
					SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(cpf, null, authorities));			
					
					filterChain.doFilter(request, response);
 				} catch (Exception ex) {
 					HttpStatus httpStatus = HttpStatus.FORBIDDEN;
 					
 					Map<String, Object> errorInfo = new HashMap<>();
 						errorInfo.put("status", httpStatus.value());
 						errorInfo.put("message", ex.getMessage());
 						errorInfo.put("path", request.getServletPath());
 						errorInfo.put("error", httpStatus.getReasonPhrase());
 					
 					response.setStatus(httpStatus.value());
 					response.setContentType(MediaType.APPLICATION_JSON_VALUE);
 					
 					new ObjectMapper().writeValue(response.getOutputStream(), errorInfo);
				}
			} else {
				filterChain.doFilter(request, response);
			}
		}
	}
	
	public static final class UserOn {

		private static String cpf;
		private static String[] roles;
		private static String[] permissions;
		
		private UserOn() {}

		public static String getCpf() {
			return cpf;
		}

		public static String[] getRoles() {
			return roles;
		}

		public static String[] getPermissions() {
			return permissions;
		}
	}
}
