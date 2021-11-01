package com.ede.edecustomerservice.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ede.edecustomerservice.dao.UserAdressDAO;
import com.ede.edecustomerservice.entity.UserAddress;
import com.ede.edecustomerservice.service.UserAddress_Service;
@Service
public class UserAdress_Impl implements UserAddress_Service {

	@Autowired
	UserAdressDAO dao;

	@Override
	public UserAddress saveAddress(UserAddress userAddress) {
		return dao.save(userAddress);
	}

}
