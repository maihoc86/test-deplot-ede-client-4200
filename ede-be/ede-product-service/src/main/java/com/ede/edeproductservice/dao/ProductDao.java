package com.ede.edeproductservice.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ede.edeproductservice.entity.Product;

public interface ProductDao extends JpaRepository<Product, String> {
	
	@Query("SELECT p FROM ProductSearch p ORDER BY dbo.fn_matcher_string(p.keysearch, :keysearch) DESC" )
	Page<Product> searchBykeysearch(@Param("keysearch") String keysearch, Pageable page);
	
	List<Product> findAllByDeleted(boolean isdelete);

}
