package com.ede.edeproductservice.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ede.edeproductservice.dao.Product_option_image_Dao;
import com.ede.edeproductservice.entity.Product_option_image;
import com.ede.edeproductservice.service.Product_option_image_service;

@Service
public class Product_option_image_impl implements Product_option_image_service {

	@Autowired
	Product_option_image_Dao dao;

	@Override
	public List<Product_option_image> findAll() {
		return dao.findAll();
	}

	@Override
	public Product_option_image save(Product_option_image product_option) {
		return dao.save(product_option);
	}

	@Override
	public List<Product_option_image> saveAll(List<Product_option_image> listTemp) {
		return dao.saveAll(listTemp);
	}

	@Override
	public Optional<Product_option_image> findById(String string) {
		return dao.findById(string);
	}

	@Override
	public List<Product_option_image> findImageByIdOption(String id) {
		return dao.findImageByIdOption(id);
	}

	@Override
	public void deleteAllImage(String id) {
		dao.deleteAllImageByOptionId(id);
	}


}
