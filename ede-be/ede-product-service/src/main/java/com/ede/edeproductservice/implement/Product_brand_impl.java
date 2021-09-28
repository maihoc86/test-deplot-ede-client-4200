package com.ede.edeproductservice.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ede.edeproductservice.dao.Product_brand_Dao;
import com.ede.edeproductservice.entity.Product_brand;
import com.ede.edeproductservice.service.Product_brand_service;

@Service
public class Product_brand_impl implements Product_brand_service {
	@Autowired
	Product_brand_Dao dao;

	@Override
	public List<Product_brand> findAll() {
		return dao.findAll();
	}


}
