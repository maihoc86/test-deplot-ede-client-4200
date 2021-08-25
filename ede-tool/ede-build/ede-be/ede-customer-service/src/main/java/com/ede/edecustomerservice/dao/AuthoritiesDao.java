package com.ede.edecustomerservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ede.edecustomerservice.entity.Authorities;

public interface AuthoritiesDao extends JpaRepository<Authorities, Integer> {

}
