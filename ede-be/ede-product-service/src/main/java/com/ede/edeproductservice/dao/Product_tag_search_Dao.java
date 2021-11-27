package com.ede.edeproductservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ede.edeproductservice.entity.Product_tag_search;

public interface Product_tag_search_Dao extends JpaRepository<Product_tag_search, String> {

	@Query(value = "Select TOP 10 id_tag,COUNT(id_tag) as count FROM product_tag_search GROUP By id_tag Order BY count DESC ", nativeQuery = true)
	List<Object> getTop10Tag();

}
