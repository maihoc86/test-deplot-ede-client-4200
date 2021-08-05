package com.ede.edecustomerservice.service;

import java.util.List;

import com.ede.edecustomerservice.entity.User;

public interface CustomerService {
	
	
	User saveUser(User entity);
	
	List<User> getAll();
}
