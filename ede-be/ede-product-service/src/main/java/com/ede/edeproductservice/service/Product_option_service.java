package com.ede.edeproductservice.service;



import java.util.List;

import com.ede.edeproductservice.entity.Product_option;

public interface Product_option_service {

	Product_option save(Product_option product_option);

	List<Product_option> findAll();

	List<Product_option> findByProductEnable(Boolean value);

	List<Product_option> findProductQuantity0();

}
