package com.ede.edeproductservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ede.edeproductservice.entity.Product_parent_category;

public interface Product_parent_category_Dao extends JpaRepository<Product_parent_category, String> {

	List<Product_parent_category> findByNameContaining(String name);

}
