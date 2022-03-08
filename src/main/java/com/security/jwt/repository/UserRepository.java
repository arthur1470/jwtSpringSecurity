package com.security.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.security.jwt.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, String>{

	Optional<UserModel> findByEmail(String email);
	Optional<UserModel> findByCpf(String cpf);
	
	@Query("select u from UserModel u where u.email = ?1 or u.cpf = ?1")
	Optional<UserModel> findByCpfOrEmail(String username);
}
