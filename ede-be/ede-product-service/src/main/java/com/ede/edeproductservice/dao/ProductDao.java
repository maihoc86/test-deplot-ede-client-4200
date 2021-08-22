package com.ede.edeproductservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ede.edeproductservice.entity.Product;

public interface ProductDao extends JpaRepository<Product, String> {
	
//	@Query("SELECT p FROM ProductSearch p ORDER BY dbo.string_distance(p.keysearch, ?1) ASC")
//	Page<Product> searchBykeysearch(String keysearch, PageRequest pageRequest);
	List<Product> findAllByDeleted(boolean isdelete);

}
