package com.ede.edeproductservice.implement;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ede.edeproductservice.dao.Product_meta_Dao;
import com.ede.edeproductservice.entity.Product_meta;
import com.ede.edeproductservice.service.Product_meta_service;

@Service
public class Product_meta_impl implements Product_meta_service {

	@Autowired
	Product_meta_Dao dao;

	@Override
	public Product_meta save(Product_meta product_meta) {
		return dao.save(product_meta);
	}

	@Override
	public Product_meta findByIdUser(String idUser, String idProduct, Date date) {
		return dao.findByIdUser(idUser, idProduct, date);
	}

}
