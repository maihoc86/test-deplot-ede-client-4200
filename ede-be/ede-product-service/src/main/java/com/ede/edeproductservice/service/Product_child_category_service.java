package com.ede.edeproductservice.service;

import java.util.List;
import java.util.Optional;

import com.ede.edeproductservice.entity.Product_child_category;
import com.ede.edeproductservice.entity.Product_parent_child_category;
import com.ede.edeproductservice.entity.Shop;

public interface Product_child_category_service {

	Product_child_category save(Product_child_category item);

	List<Product_child_category> findAll();

	Iterable<Product_child_category> saveAll(List<Product_child_category> listTemp);

	Optional<Product_child_category> findById(String string);

	List<Product_child_category> findByNameContaining(String name);


	Product_child_category deleteChild(String id);

	Product_child_category update(Product_child_category childCategory);

	boolean existsById(String id);

	List<Product_child_category> findByIsdeleteFalse();

	List<Product_child_category> findByIdParentChild(String id);

	Product_parent_child_category findParent(String id);

	List<Product_child_category> findAllByShop(String id);


}
