package com.ede.edecustomerservice.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ede.edecustomerservice.dao.CartDao;
import com.ede.edecustomerservice.entity.Cart;
import com.ede.edecustomerservice.entity.Cart_item;
import com.ede.edecustomerservice.entity.User;
import com.ede.edecustomerservice.service.Cart_Service;
@Service
public class CartImpl implements Cart_Service{
	@Autowired
	CartDao dao;
	@Override
	public void save(Cart cart) {
		dao.save(cart);
	}
	@Override
	public Cart findByUserId(String id) {	
		return dao.findByUserid(id);
	}
	@Override
	public List<Object> getAllItemByUserID(String id) {

		return dao.getAllItemByUserID(id);
	}

}
