package com.ede.edeproductservice.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ede.edeproductservice.dao.Order_detail_DAO;
import com.ede.edeproductservice.service.Order_detail_service;

@Service
public class Order_detail_impl implements Order_detail_service {

	@Autowired
	Order_detail_DAO dao;
	
	@Override
	public Order_detail_service findProductOption(String id) {
		return dao.findProductOption(id);
	}

}
