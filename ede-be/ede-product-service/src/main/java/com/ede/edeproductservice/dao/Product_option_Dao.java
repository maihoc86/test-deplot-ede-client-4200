package com.ede.edeproductservice.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.entity.Product_child_category;
import com.ede.edeproductservice.entity.Product_option;
import com.ede.edeproductservice.entity.Shop;

public interface Product_option_Dao extends JpaRepository<Product_option, String> {

	@Query("SELECT o FROM Product_option o where o.product.enable =: value")
	List<Product_option> findByProductEnable(Boolean value);

	@Query("SELECT o FROM Product_option o where o.quantity = 0")
	List<Product_option> findProductQuantity0();

	@Query("SELECT o FROM Product_option o where o.product.location =:location and o.product.child_category.name =:category and o.product.brand.name =:brand")
	List<Product_option> filterProductShopByCustomerAND(String location, String category, String brand);

	@Query("SELECT o FROM Product_option o where o.product.location =:location or o.product.child_category.name =:category or o.product.brand.name =:brand")
	List<Product_option> filterProductShopByCustomerOR(String location, String category, String brand);

	@Query("select o from Product_option o where o.product.id = ?1")
	Product_option findByIdbyProduct(String id);

	@Query("select o from Product_option o where o.product.shop = ?1")
	List<Product_option> finByShop(Shop shop);

	@Query("select o from Product_option o where o.product.shop = ?1")
	Page<Product_option> finAllByShop(Shop shop, PageRequest of);

	@Query("select o from Product_option o where o.product.deleted = false and o.is_delete = false and o.product.shop = ?1 and o.product.enable = ?2 ")
	Page<Product_option> findProductEnableShop(Shop shop, Boolean value, PageRequest of);

	@Query("select o from Product_option o where o.product.deleted = false and o.is_delete = false and  o.product.shop = ?1 and o.quantity = 0")
	Page<Product_option> findProductQuantity0Shop(Shop shop, PageRequest of);

	@Query("select o.product from Product_option o where o.id = ?1")
	Product findProductById(String id);

	@Query("select o.product.child_category from Product_option o where o.id = ?1")
	Product_child_category findChildCategoryById(String id);

	@Query("SELECT count(o) FROM Product_option o where o.product.id = ?1 ")
	int countItemByProductID(String id);
	
	@Query("SELECT p FROM Product_option p WHERE p.product.shop.id =:id_shop AND  p.is_delete = false AND p.product.deleted = false AND p.product.enable = true")
	Page<Product_option> listAllProductByCustomer(String id_shop,Pageable page);


	
//	@Query("SELECT o from Product_option o where o")
//	Page<Product_option> listAll(PageRequest of);

}
