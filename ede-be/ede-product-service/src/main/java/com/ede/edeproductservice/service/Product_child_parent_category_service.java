package com.ede.edeproductservice.service;

import java.util.List;
import java.util.Optional;

import com.ede.edeproductservice.entity.Product_parent_category;
import com.ede.edeproductservice.entity.Product_parent_child_category;

public interface Product_child_parent_category_service {

	Product_parent_child_category save(Product_parent_child_category child_parent_category);

	List<Product_parent_child_category> findAll();

	Iterable<Product_parent_child_category> saveAll(List<Product_parent_child_category> child_parent_category);

	Optional<Product_parent_child_category> findById(String string);

	List<Product_parent_child_category> findByNameContaining(String name);

	Product_parent_child_category deleteParent_Child(String id);

	Product_parent_child_category update(Product_parent_child_category parentChildCategory);

	boolean existsById(String id);

	List<Product_parent_child_category> findByIsdeleteFalse();

	List<Product_parent_child_category> findByIdParent(String id);

	Product_parent_category findParent(String id);

	

}
