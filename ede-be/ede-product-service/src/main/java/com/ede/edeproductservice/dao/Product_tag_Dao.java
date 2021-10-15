package com.ede.edeproductservice.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ede.edeproductservice.entity.Product_tag;

public interface Product_tag_Dao extends JpaRepository<Product_tag, String> {

	
	@Query("SELECT o FROM Product_tag o where o.producttag.id =:id ")
	List<Product_tag> findTagByidProduct(String id);

	@Transactional
	@Modifying
	@Query("DELETE FROM Product_tag o where o.producttag.id =:id")
	void deleteAllTagByIdProduct(String id);

}
