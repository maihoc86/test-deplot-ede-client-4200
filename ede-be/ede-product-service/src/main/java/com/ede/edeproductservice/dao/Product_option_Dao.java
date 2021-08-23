package com.ede.edeproductservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ede.edeproductservice.entity.Product_option;

public interface Product_option_Dao extends JpaRepository<Product_option, String> {

	@Query("SELECT o FROM Product_option o where o.product.enable =: value")
	List<Product_option> findByProductEnable(Boolean value);

	@Query("SELECT o FROM Product_option o where o.quantity = 0")
	List<Product_option> findProductQuantity0();

}
