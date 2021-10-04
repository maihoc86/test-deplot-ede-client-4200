package com.ede.edeproductservice.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.entity.Product_brand;
import com.ede.edeproductservice.entity.Product_child_category;
import com.ede.edeproductservice.entity.extend.ProductSearch;

public interface ProductDao extends JpaRepository<Product, String> {
	@Query("SELECT p FROM ProductSearch p WHERE p.enable = true AND p.deleted = false ORDER BY dbo.fn_matcher_string(p.keysearch, :keysearch) DESC")
	Page<ProductSearch> searchBykeysearch(@Param("keysearch") String keysearch, Pageable page);

	@Query("SELECT p FROM ProductSearch p WHERE p.enable = true and p.deleted = false and p.shop.id=:id")
	Page<ProductSearch> listAllProductShopByCustomer(String id, Pageable page);

	/**
	 * @author thái học
	 *
	 * 
	 */
	@Query("SELECT p FROM ProductSearch p WHERE p.enable = true and p.deleted = false and p.shop.id=:id_shop and p.childCategory.id=:valueCate")
	Page<ProductSearch> filterProductShopByCustomerCategory(String valueCate, String id_shop, Pageable of);

	@Query("SELECT p FROM ProductSearch p WHERE p.enable = true and p.deleted = false and p.childCategory.id=:valueCate")
	Page<ProductSearch> filterProductShopByCustomerCategory2(String valueCate, Pageable of);

	/**
	 * @author thái học
	 *
	 * 
	 */
	@Query("SELECT p FROM ProductSearch p WHERE p.enable = true and p.deleted = false and p.location IN (:names) and p.shop.id=:id")
	Page<ProductSearch> filterProductShopByCustomerLocation(List<String> names, String id, Pageable of);

	@Query("SELECT p FROM ProductSearch p WHERE p.enable = true and p.deleted = false and p.shop.id=:id and p.brand.id IN (:brand)")
	Page<ProductSearch> filterProductShopByCustomerBrand(List<String> brand, String id, Pageable of);

	@Query("SELECT p FROM ProductSearch p WHERE p.enable = true and p.deleted = false and p.location IN (:location) and p.childCategory.id =:category and p.shop.id=:id")
	Page<ProductSearch> filterProductShopByCustomerLocationAndCategory(List<String> location, String category,
			String id, Pageable of);

	@Query("SELECT p FROM ProductSearch p WHERE p.enable = true and p.deleted = false and p.childCategory.id =:category and p.brand.id IN (:brand) and p.shop.id=:id")
	Page<ProductSearch> filterProductShopByCustomerCategoryAndBrand(String category, List<String> brand, String id,
			Pageable of);

	@Query("SELECT p FROM ProductSearch p WHERE p.enable = true and p.deleted = false and p.location IN (:location) and p.brand.id IN (:brand) and p.shop.id=:id")
	Page<ProductSearch> filterProductShopByCustomerLocationAndBrand(List<String> location, List<String> brand,
			String id, Pageable of);

	@Query("SELECT p FROM ProductSearch p WHERE p.enable = true and p.deleted = false and p.location IN (:location) and p.childCategory.id =:category and p.brand.id IN (:brand) and p.shop.id=:id")
	Page<ProductSearch> filterProductShopByCustomerLocationAndCategoryAndBrand(List<String> location, String category,
			List<String> brand, String id, Pageable of);

	/*****************************************/
	@Query("SELECT p FROM Product p WHERE p.enable = true AND p.deleted = false")
	Page<Product> listAll(Pageable page);

	List<Product> findAllByDeleted(boolean isdelete);

	@Query("select o.child_category from Product o where o.id=?1 ")
	Product_child_category findCategorybyIDProduct(String id);

	@Query("SELECT o from Product o where o.shop.id =:id")
	List<Product> findByShop(String id);

	/**
	 * @author thái học
	 *
	 * 
	 */
	@Query("SELECT p FROM ProductSearch p WHERE p.deleted = false")
	Page<ProductSearch> listAllProductSearch(Pageable page);

	/**
	 * @author thái học
	 *
	 * 
	 */
	@Query("UPDATE Product p SET p.deleted = true where id=:id")
	Product updateStatus(String id);

	@Query("SELECT ps from ProductSearch ps where ps.id = :id")
	ProductSearch findByProductSearchId(@Param("id") String id);

	@Query("SELECT DISTINCT o.brand FROM Product o where o.shop.id=:valueIdShop")
	List<Product_brand> selectAllBrandInShop(String valueIdShop);

	@Query("select o from Product o where o.child_category.id=?1")
	Page<Product> findByCategory(String id, PageRequest pageRequest);

	@Query("SELECT p FROM ProductSearch p WHERE p.enable = true and p.deleted = false and p.shop.id=:id_shop and p.childCategory.id=:valueCate")
	Page<ProductSearch> filterProductShopNewByCustomerCategory(String valueCate, String id_shop, Pageable of);

	@Query("Select p FROM ProductSearch p")
	Page<ProductSearch> testFilterProductNew(Pageable of);

}
