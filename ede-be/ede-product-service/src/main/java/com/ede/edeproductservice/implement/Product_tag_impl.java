package com.ede.edeproductservice.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ede.edeproductservice.dao.Product_tag_Dao;
import com.ede.edeproductservice.entity.Product_option_image;
import com.ede.edeproductservice.entity.Product_tag;
import com.ede.edeproductservice.service.Product_Tag_service;

@Service
public class Product_tag_impl implements Product_Tag_service {

	@Autowired
	Product_tag_Dao dao;

	@Override
	public Product_tag save(Product_tag product_tag) {
		return dao.save(product_tag);
	}

	@Override
	public List<Product_tag> findAll() {
		return dao.findAll();
	}

	@Override
	public Optional<Product_tag> findById(String string) {
		return dao.findById(string);
	}

	@Override
	public List<Product_tag> findTagByidProduct(String id) {
		return dao.findTagByidProduct(id);
	}

}
