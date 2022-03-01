package com.security.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.jwt.model.RoleModel;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Long>{

	Optional<RoleModel> findByName(String name);
}
