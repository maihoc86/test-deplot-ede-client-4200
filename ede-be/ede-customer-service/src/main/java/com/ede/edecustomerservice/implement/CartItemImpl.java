package com.ede.edecustomerservice.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ede.edecustomerservice.dao.CartItemDao;
import com.ede.edecustomerservice.entity.Cart_item;
import com.ede.edecustomerservice.service.Cart_item_Service;

@Service
public class CartItemImpl implements Cart_item_Service {
	@Autowired
	CartItemDao dao;
	@Override
	public void save(Cart_item cart_item) {
		dao.save(cart_item);
	}
	@Override
	public void clearByCartId(String id) {
		dao.deleteByCartID(id);
	}
	@Override
	public void deleteByCartID(String id) {
		dao.deleteByCartID(id);
		
	}
}
