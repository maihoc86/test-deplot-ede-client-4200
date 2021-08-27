package com.ede.edeproductservice.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.entity.Product_child_category;

public interface ProductService {

	List<Product> findAll();

	Page<Product> searchByKeysearch(String keysearch, Pageable page);

	Product save(Product product);

	List<Product> findAllIsdeleteFalse();

	Product findById(String id);

	Product_child_category findCategorybyIDProduct(String id);
	



}
