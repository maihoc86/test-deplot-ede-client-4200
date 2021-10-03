package com.ede.edeproductservice.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ede.edeproductservice.dao.UserDao;
import com.ede.edeproductservice.entity.User;
import com.ede.edeproductservice.service.User_Service;


@Service
public class User_impl implements User_Service{

	
	@Autowired
	UserDao dao;
	
	@Override
	public User findById(String idUser) {
		return dao.findById(idUser).get();
	}

}
