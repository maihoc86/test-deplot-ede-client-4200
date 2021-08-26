package com.ede.edeproductservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ede.edeproductservice.entity.Product_tag;

public interface Product_tag_Dao extends JpaRepository<Product_tag, String> {

	
	@Query("SELECT o FROM Product_tag o where o.producttag.id =:id ")
	List<Product_tag> findTagByidProduct(String id);

}
