package com.ede.edecustomerservice.service;

import java.util.List;

import com.ede.edecustomerservice.entity.UserAddress;

public interface UserAddress_Service {

	UserAddress saveAddress(UserAddress userAddress);

	List<UserAddress> getAllAddressByUser(String userId);

	void deleteById(String id);

	UserAddress getAddressByUser(String userId , String address);
	
	UserAddress getAddressByUserId(String userId , String addressId);
	UserAddress findById(String id);


}
