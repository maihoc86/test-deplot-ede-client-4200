package com.ede.edecustomerservice.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ede.edecustomerservice.dao.UserDao;
import com.ede.edecustomerservice.entity.User;
import com.ede.edecustomerservice.service.CustomerService;

@Service
public class CustomerImpl implements CustomerService {
	
	
	@Autowired
	UserDao dao;

	@Override
	public User saveUser(User entity) {
		return dao.saveAndFlush(entity);
	}
	
	
	
}
