package com.security.jwt.security.userdetails;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.security.jwt.model.PermissionModel;

public class CustomUserDetail extends User {
	private static final long serialVersionUID = -3959754090781417210L;
	
	private List<PermissionModel> permissions;
	
	public CustomUserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities, List<PermissionModel> permissions) {
		super(username, password, true, true, true, true, authorities);
		
		this.permissions = permissions;
	}
	
	public List<PermissionModel> getPermissions() {
		return permissions;
	}
}
