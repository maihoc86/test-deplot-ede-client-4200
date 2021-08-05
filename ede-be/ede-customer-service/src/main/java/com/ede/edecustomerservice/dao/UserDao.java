package com.ede.edecustomerservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ede.edecustomerservice.entity.User;

public interface UserDao extends JpaRepository<User, String>{
}
