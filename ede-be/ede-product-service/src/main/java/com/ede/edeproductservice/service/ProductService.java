package com.ede.edeproductservice.service;

import java.util.List;

import com.ede.edeproductservice.entity.Product;

public interface ProductService {

	List<Product> findAll();

	List<Product> findAllIsdeleteFalse();

}
