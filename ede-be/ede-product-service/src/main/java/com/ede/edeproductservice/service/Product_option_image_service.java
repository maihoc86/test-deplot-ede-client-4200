package com.ede.edeproductservice.service;

import java.util.List;

import com.ede.edeproductservice.entity.Product_option_image;

public interface Product_option_image_service {

	List<Product_option_image> findAll();

	Product_option_image save(Product_option_image product_option);

}
