package com.ede.edeproductservice.service;

import java.util.List;

import com.ede.edeproductservice.entity.Product_parent_child_category;

public interface Product_child_parent_category_service {

	Product_parent_child_category save(Product_parent_child_category child_parent_category);

	List<Product_parent_child_category> findAll();

	List<Product_parent_child_category> saveAll(List<Product_parent_child_category> child_parent_category);



}
