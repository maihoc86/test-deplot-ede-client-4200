package com.ede.edecustomerservice.service;

import java.util.List;

import com.ede.edecustomerservice.entity.Cart_item;

public interface Cart_item_Service {

	void save(Cart_item cart_item);

	void clearByCartId(String id);

	void deleteByCartID(String id);


}
