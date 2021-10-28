package com.ede.edecustomerservice.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ede.edecustomerservice.dao.ProductOptionDao;
import com.ede.edecustomerservice.entity.Product_option;
import com.ede.edecustomerservice.service.Product_Option_Service;

@Service
public class ProductOptionImpl implements Product_Option_Service{
	@Autowired
	ProductOptionDao dao;
	@Override
	public Product_option findById(String id) {		
		return dao.findById(id).get();
	}

}
