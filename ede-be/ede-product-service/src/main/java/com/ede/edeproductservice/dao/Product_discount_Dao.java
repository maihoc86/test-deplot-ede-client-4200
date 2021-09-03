package com.ede.edeproductservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ede.edeproductservice.entity.Product_discount;

public interface Product_discount_Dao extends JpaRepository<Product_discount, String> {

	
	@Query("SELECT o FROM Product_discount o where o.productdiscount.id =:id")
	List<Product_discount> findByIdProduct(String id);

	/**
	 * @author thái học
	 *
	 * 
	 */
	@Query("SELECT o FROM Product_discount o where o.product.id=:id")
	List<Product_discount> findAllByShop(String id);

}
