package com.ede.edecustomerservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.ede.edecustomerservice.entity.User;

public interface UserDao extends JpaRepository<User, String>{

	User findByEmailLike(String email);

	User findByUsername(String username);
	User findByEmail(String email);
	User findByPhone(String phone);
	
	Long deleteByUsername(String username);

	List<User> findByUsernameContaining(String username);
}
