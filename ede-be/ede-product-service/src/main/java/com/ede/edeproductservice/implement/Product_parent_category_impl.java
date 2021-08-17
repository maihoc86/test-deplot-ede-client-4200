package com.ede.edeproductservice.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ede.edeproductservice.dao.Product_parent_category_Dao;
import com.ede.edeproductservice.entity.Product_parent_category;
import com.ede.edeproductservice.service.Product_parent_category_service;

@Service
@Transactional
public class Product_parent_category_impl implements Product_parent_category_service {

	@Autowired
	Product_parent_category_Dao dao;

	@Override
	public Product_parent_category save(Product_parent_category parent_category) {
		return dao.save(parent_category);
	}

	@Override
	public List<Product_parent_category> findAll() {
		return dao.findAll();
	}

	@Override
	public Optional<Product_parent_category> findById(String id) {
		return dao.findById(id);
	}

	@Override
	public Product_parent_category deleteParent(String id) {
		Product_parent_category pr = dao.findById(id).get();
		pr.setIs_delete(true);
		dao.save(pr);
		return pr;
	}

	@Override
	public List<Product_parent_category> findByNameContaining(String name) {
		// TODO Auto-generated method stub
		return dao.findByNameContaining(name);
	}

}
