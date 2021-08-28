package com.ede.edeproductservice.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.entity.Product_child_category;
import com.ede.edeproductservice.entity.extend.ProductSearch;

public interface ProductDao extends JpaRepository<Product, String> {
	
	@Query("SELECT p FROM ProductSearch p ORDER BY dbo.fn_matcher_string(p.keysearch, :keysearch) DESC" )
	Page<ProductSearch> searchBykeysearch(@Param("keysearch") String keysearch, Pageable page);
	
	List<Product> findAllByDeleted(boolean isdelete);
	@Query("select o.child_category from Product o where o.id=?1 ")
	Product_child_category findCategorybyIDProduct(String id);

}
