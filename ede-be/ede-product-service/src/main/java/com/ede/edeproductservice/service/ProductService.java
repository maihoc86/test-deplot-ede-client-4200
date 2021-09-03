package com.ede.edeproductservice.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.entity.Product_child_category;
import com.ede.edeproductservice.entity.extend.ProductSearch;

public interface ProductService {

	Page<Product> listAll(Pageable pageable);

	Page<ProductSearch> searchByKeysearch(String keysearch, Pageable page);

	Product save(Product product);

	List<Product> findAllIsdeleteFalse();

	Product findById(String id);

	Product_child_category findCategorybyIDProduct(String id);


	List<Product> findByShop(String id);

	Page<Product> listAllProductShopByCustomer(String id, PageRequest of);

	Page<Product> listAll(PageRequest of);


	



}
