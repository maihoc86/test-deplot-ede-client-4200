package com.ede.edeproductservice.service;

import java.util.List;

import com.ede.edeproductservice.entity.Product_child_category;

public interface Product_child_category_service {

	Product_child_category save(Product_child_category item);

	List<Product_child_category> findAll();

}
