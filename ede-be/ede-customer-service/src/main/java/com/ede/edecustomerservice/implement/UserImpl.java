package com.ede.edecustomerservice.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ede.edecustomerservice.dao.UserDao;
import com.ede.edecustomerservice.entity.User;
import com.ede.edecustomerservice.service.User_Service;

@Service
public class UserImpl implements User_Service {
	@Autowired
	UserDao dao;
	@Override
	public User findById(String id) {
		return dao.findById(id).get();
	}

}
