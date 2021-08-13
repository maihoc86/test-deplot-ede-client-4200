package com.ede.edeproductservice.implement;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ede.edeproductservice.dao.Product_child_category_Dao;
import com.ede.edeproductservice.entity.Product_child_category;
import com.ede.edeproductservice.service.Product_child_category_service;

@Service
@Transactional
public class Product_child_category_impl implements Product_child_category_service {

	@Autowired
	Product_child_category_Dao dao;

	@Override
	public Product_child_category save(Product_child_category item) {
		return dao.save(item);
	}

	@Override
	public List<Product_child_category> findAll() {
		return dao.findAll();
	}

	@Override
	public Iterable<Product_child_category> saveAll(List<Product_child_category> listTemp) {
		return dao.saveAll(listTemp);
	}

}
