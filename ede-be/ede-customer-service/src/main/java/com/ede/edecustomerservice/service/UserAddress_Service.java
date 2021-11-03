package com.ede.edecustomerservice.service;

import java.util.List;

import com.ede.edecustomerservice.entity.UserAddress;

public interface UserAddress_Service {

	UserAddress saveAddress(UserAddress userAddress);

	List<UserAddress> findByUserId(String userId,String address);

	void deleteById(String id);

	UserAddress findByUserId(String id);

}
