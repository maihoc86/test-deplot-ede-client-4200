package com.ede.edecustomerservice.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ede.edecustomerservice.dao.UserAdressDAO;
import com.ede.edecustomerservice.entity.UserAddress;
import com.ede.edecustomerservice.service.UserAddress_Service;
@Service
public class UserAddress_Impl implements UserAddress_Service {

	@Autowired
	UserAdressDAO dao;

	@Override
	public UserAddress saveAddress(UserAddress userAddress) {
		return dao.save(userAddress);
	}

	@Override
	public List<UserAddress> getAllAddressByUser(String userId) {
		return dao.getAllAddressByUser(userId);
	}

	@Override
	public void deleteById(String id) {
		 dao.deleteById(id);
		
	}

	@Override
	public UserAddress getAddressByUser(String userId, String address) {
		return dao.getAddressByUser(userId,address);
	}


}
