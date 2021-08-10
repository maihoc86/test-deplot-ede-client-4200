package com.ede.edecustomerservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ede.edecustomerservice.entity.Roles;

public interface RoleDao extends JpaRepository<Roles, String> {

}
