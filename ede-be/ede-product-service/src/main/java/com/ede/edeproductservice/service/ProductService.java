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

	Page<ProductSearch> listAllProductShopByCustomer(String id, Pageable page);

	Product save(Product product);

	List<Product> findAllIsdeleteFalse();

	Product findById(String id);

	Product_child_category findCategorybyIDProduct(String id);

	List<Product> findByShop(String id);

	Page<Product> listAll(PageRequest of);

	/**
	 * @author thái học
	 *
	 * 
	 */
	Page<ProductSearch> filterProductShopByCustomerCategory(String valueCate, String id_shop, Pageable of);
	Page<ProductSearch> filterProductShopByCustomerLocation(List<String> names, String id, Pageable of);
	Page<ProductSearch> filterProductShopByCustomerBrand(List<String> valueBrand, String id, Pageable of);
	Page<ProductSearch> filterProductShopByCustomerLocationAndCategory(List<String> names,String category, String id, Pageable of);
	Page<ProductSearch> filterProductShopByCustomerCategoryAndBrand(String category,List<String> valueBrand, String id, Pageable of);
	Page<ProductSearch> filterProductShopByCustomerLocationAndBrand(List<String> names,List<String> valueBrand, String id, Pageable of);
	Page<ProductSearch> filterProductShopByCustomerLocationAndCategoryAndBrand(List<String> names,String category,List<String> brand, String id, Pageable of);

	ProductSearch findByProductSearchId(String id);
	



}
