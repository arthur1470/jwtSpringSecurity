package com.security.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.jwt.model.PermissionModel;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionModel, Long> {
	Optional<PermissionModel> findByName(String name);
}
