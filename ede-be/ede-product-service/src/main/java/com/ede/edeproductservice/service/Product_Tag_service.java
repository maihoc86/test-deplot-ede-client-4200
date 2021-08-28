package com.ede.edeproductservice.service;

import java.util.List;
import java.util.Optional;

import com.ede.edeproductservice.entity.Product_tag;

public interface Product_Tag_service {

	Product_tag save(Product_tag product_tag);

	List<Product_tag> findAll();

	Optional<Product_tag> findById(String string);
	
	List<Product_tag> findTagByidProduct(String id);

	void deleteAllTagByIdProduct(String id);

}
