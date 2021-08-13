package com.ede.edeproductservice.service;

import java.util.List;

import com.ede.edeproductservice.entity.Product_parent_category;

public interface Product_parent_category_service {

	Product_parent_category save(Product_parent_category parent_category);

	List<Product_parent_category> findAll();

}
