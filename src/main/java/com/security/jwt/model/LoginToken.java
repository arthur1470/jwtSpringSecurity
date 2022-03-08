package com.security.jwt.model;

import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@Entity
@NoArgsConstructor
//@Table(name = "login_token")
public class LoginToken {

	private Integer code;
	private UserModel user;
	private Long generatedTimestamp;
	private Long expirationTimestamp;
	
	public LoginToken(UserModel user) {
		this.user = user;
	}
	
	@PrePersist
	private void prePersist() {
		this.code = new Random().nextInt(10000, 99999);
		this.generatedTimestamp = System.currentTimeMillis();
		this.expirationTimestamp = System.currentTimeMillis() + (60 * 1000); // 1 min
	}
}
