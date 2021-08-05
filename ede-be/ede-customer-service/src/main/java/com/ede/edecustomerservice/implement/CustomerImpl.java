package com.ede.edecustomerservice.implement;

import java.util.List;

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
		return dao.save(entity);
	}

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

}
