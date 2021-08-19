package com.ede.edeproductservice.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ede.edeproductservice.entity.Product;

public interface ProductDao extends JpaRepository<Product, String> {
	
	@Query("SELECT p FROM ProductSearch p ORDER BY dbo.string_distance(p.keysearch, ?1) ASC")
	Page<Product> searchBykeysearch(String keysearch, PageRequest pageRequest);
	
}
