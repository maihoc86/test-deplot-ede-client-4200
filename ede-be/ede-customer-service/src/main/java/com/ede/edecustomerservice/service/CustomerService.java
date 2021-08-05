package com.ede.edecustomerservice.service;

import com.ede.edecustomerservice.entity.User;

public interface CustomerService {
	
	
	User saveUser(User entity);

	boolean resetPasswordToken(User user);

	boolean resetPasswordOtp(User user);

}
