package com.ede.edecustomerservice.service;

import java.util.List;

import com.ede.edecustomerservice.entity.User;

public interface CustomerService {

	User saveUser(User entity);

	boolean resetPasswordToken(User user);

	boolean resetPasswordOtp(User user);
	
	User findByEmailLike(String email);
	
	List<User> getAll();

	User updateUserById(User userUpdate);
	
}
