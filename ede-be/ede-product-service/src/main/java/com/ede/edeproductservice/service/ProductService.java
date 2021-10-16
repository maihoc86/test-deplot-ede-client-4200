package com.ede.edeproductservice.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.ede.edeproductservice.entity.Evaluate;
import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.entity.Product_brand;
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

	
	/**
	 * Hàm filter sản phẩm mới
	 * @author Thái Học
	 */
	Page<ProductSearch> filterProductShopNewByCustomerCategory(String valueCate, String id_shop, Pageable of);
//	Page<ProductSearch> filterProductShopNewByCustomerLocation(List<String> names, String id, Pageable of);
//	Page<ProductSearch> filterProductShopNewByCustomerBrand(List<String> valueBrand, String id, Pageable of);
//	Page<ProductSearch> filterProductShopNewByCustomerLocationAndCategory(List<String> names,String category, String id, Pageable of);
//	Page<ProductSearch> filterProductShopNewByCustomerCategoryAndBrand(String category,List<String> valueBrand, String id, Pageable of);
//	Page<ProductSearch> filterProductShopNewByCustomerLocationAndBrand(List<String> names,List<String> valueBrand, String id, Pageable of);
//	Page<ProductSearch> filterProductShopNewByCustomerLocationAndCategoryAndBrand(List<String> names,String category,List<String> brand, String id, Pageable of);

	
	/**
	 * @author thái học
	 *
	 * 
	 */
	List<Product> listAll();

	/**
	 * @author thái học
	 *
	 * 
	 */
	Page<ProductSearch> listAllProductSearch(Pageable page);

	/**
	 * @author thái học
	 *
	 * 
	 */
	Product deleteById(String id);
	ProductSearch findByProductSearchId(String id);

	List<Product_brand> selectAllBrandInShop(String valueIdShop);

	Page<Product> findByCategory(String id, PageRequest pageRequest);

	Page<ProductSearch> filterProductShopByCustomerCategory2(String id, PageRequest pageRequest);

	Page<ProductSearch> testFilterProductNew(Pageable of);
	List<ProductSearch> getProductAllByIdShop(String id);

	/**
	 * @author Kim Thanh
	 *
	 * 
	 */
	List<Evaluate> findAllCommnentByIdProduct(String id);

	Page<ProductSearch> getProductNewByIdShop(String id, PageRequest pageRequest);

	



}
