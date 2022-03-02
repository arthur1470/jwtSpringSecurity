package com.security.jwt.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "usuario")
public class UserModel {

	@Id
	private String id;

	private String name;

	@Column(unique = true)
	private String email;
	
	@Column(unique = true)
	private String cpf;
	
	private String password;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<RoleModel> roles;

	public UserModel(String name, String email, String cpf, String password) {
		this.name = name;
		this.email = email;
		this.cpf = cpf;
		this.password = password;
	}

	@PrePersist
	private void prePersist() {
		this.id = UUID.randomUUID().toString();
		this.createdAt = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "UserModel [id=" + id + ", name=" + name + ", email=" + email + ", cpf=" + cpf + ", password=" + password
				+ ", createdAt=" + createdAt + ", roles=" + roles + "]";
	}
}
