package com.ede.edeproductservice.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ede.edeproductservice.dao.Product_parent_child_category_Dao;
import com.ede.edeproductservice.entity.Product_parent_category;
import com.ede.edeproductservice.entity.Product_parent_child_category;
import com.ede.edeproductservice.service.Product_child_parent_category_service;

@Transactional
@Service
public class Product_parent_child_category_impl implements Product_child_parent_category_service {

	@Autowired
	Product_parent_child_category_Dao dao;

	@Override
	public Product_parent_child_category save(Product_parent_child_category child_parent_category) {
		return dao.save(child_parent_category);
	}

	@Override
	public List<Product_parent_child_category> findAll() {
		return dao.findAll();
	}

	@Override
	public Iterable<Product_parent_child_category> saveAll(List<Product_parent_child_category> child_parent_category) {
		return dao.saveAll(child_parent_category);
	}

	@Override
	public Optional<Product_parent_child_category> findById(String string) {
		return dao.findById(string);
	}

	@Override
	public List<Product_parent_child_category> findByNameContaining(String name) {
		// TODO Auto-generated method stub
		return dao.findByNameContaining(name);
	}

	@Override
	public Product_parent_child_category deleteParent_Child(String id) {
		Product_parent_child_category e = dao.findById(id).get();
		e.setIs_delete(true);
		dao.save(e);
		return e;
	}


	

}
