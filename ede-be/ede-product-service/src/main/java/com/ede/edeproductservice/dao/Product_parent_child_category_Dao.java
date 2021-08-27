package com.ede.edeproductservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ede.edeproductservice.entity.Product_parent_category;
import com.ede.edeproductservice.entity.Product_parent_child_category;

public interface Product_parent_child_category_Dao extends JpaRepository<Product_parent_child_category, String> {
	
	List<Product_parent_child_category> findByNameContaining(String name);
	
	@Query("Select o from Product_parent_child_category o where o.is_delete = false")
	List<Product_parent_child_category> findByIsdeleteFalse();

	@Query("SELECT o from Product_parent_child_category o where o.parentcategory.id = ?1")
	List<Product_parent_child_category> findByIdParent(String id);
	@Query("select o.parentcategory from Product_parent_child_category o where o.id=?1")
	Product_parent_category findParent(String id);

}
