package com.ede.edecustomerservice.service;

import java.util.List;
import java.util.Optional;

import com.ede.edecustomerservice.entity.Roles;
import com.ede.edecustomerservice.entity.User;

public interface CustomerService {

	User saveUser(User entity);

	boolean resetPasswordToken(User user);

	boolean resetPasswordOtp(User user);
	
	User findByEmailLike(String email);
	
	List<User> getAll();
	User updateUserById(User userUpdate);
	User findByUsername(String username);
	User findByEmail(String username);
	User findByPhone(String phone);

	List<User> findAll();

	User deleteByUsername(String username);

	




	

	
}
