package com.ede.edecustomerservice.service;

import java.util.List;

import com.ede.edecustomerservice.entity.Cart;

public interface Cart_Service {

	void save(Cart cart_item);

	Cart findByUserId(String user);

	List<Object> getAllItemByUserID(String id);

}
