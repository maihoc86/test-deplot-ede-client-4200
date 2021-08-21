package com.ede.edeproductservice.service;

import java.util.List;
import java.util.Optional;

import com.ede.edeproductservice.entity.Product_parent_category;

public interface Product_parent_category_service {

	Product_parent_category save(Product_parent_category parent_category);

	List<Product_parent_category> findAll();

	Optional<Product_parent_category> findById(String id);

	Product_parent_category deleteParent(String id);
	List<Product_parent_category> findByNameContaining(String name);

	Product_parent_category update(Product_parent_category parentCategory);

	boolean existsById(String id);

	List<Product_parent_category> findByIsdeleteFalse();



}
