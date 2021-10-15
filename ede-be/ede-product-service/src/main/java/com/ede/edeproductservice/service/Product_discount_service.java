package com.ede.edeproductservice.service;

import java.sql.Date;
import java.util.List;

import com.ede.edeproductservice.entity.Product_discount;

public interface Product_discount_service {

	Product_discount save(Product_discount product_discount);

	List<Product_discount> findAll();

	/**
	 * @author Thái Học
	 * @param id
	 * @return
	 */
	List<Product_discount> findByIdProduct(String id,Date date);

	

}
