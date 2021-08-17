package com.ede.edeproductservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ede.edeproductservice.entity.Product_child_category;

public interface Product_child_category_Dao extends JpaRepository<Product_child_category, String> {

	List<Product_child_category> findByNameContaining(String name);

}


