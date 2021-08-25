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

	@Query("SELECT o FROM Product_option o where o.product.location =:location and o.product.child_category.name =:category and o.product.brand.name =:brand")
	List<Product_option> filterProductShopByCustomerAND(String location, String category, String brand);

	
	@Query("SELECT o FROM Product_option o where o.product.location =:location or o.product.child_category.name =:category or o.product.brand.name =:brand")
	List<Product_option> filterProductShopByCustomerOR(String location, String category, String brand);
}
