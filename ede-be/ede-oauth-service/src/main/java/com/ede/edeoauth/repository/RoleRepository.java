package com.ede.edeoauth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ede.edeoauth.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String>{
	Optional<Role> findByName(String name);
}
