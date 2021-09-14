package com.ede.edeproductservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ede.edeproductservice.entity.Product_discount;

public interface Product_discount_Dao extends JpaRepository<Product_discount, String> {

	/**
	 * @author thái học
	 *
	 * 
	 */
	@Query("SELECT o FROM Product_discount o where o.productdiscount.shop.id =:id")
	Product_discount findAllByShop(String id);



}
