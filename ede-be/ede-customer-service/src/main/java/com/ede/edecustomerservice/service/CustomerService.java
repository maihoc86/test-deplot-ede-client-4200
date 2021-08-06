package com.ede.edecustomerservice.service;

import java.util.List;

import com.ede.edecustomerservice.entity.User;

public interface CustomerService {

	User saveUser(User entity);

	boolean resetPasswordToken(User user);

	boolean resetPasswordOtp(User user);
	
	List<User> getAll();
	
	User findByUsername(String username);
	User findByEmail(String username);
	User findByPhone(String phone);
}
