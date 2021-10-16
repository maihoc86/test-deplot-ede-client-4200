package com.ede.edeproductservice.dao;


import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ede.edeproductservice.entity.Product_discount;
import com.ede.edeproductservice.entity.extend.ProductSearch;

public interface Product_discount_Dao extends JpaRepository<Product_discount, String> {

	/**
	 * @author thái học
	 *
	 * 
	 */
	@Query("SELECT o FROM Product_discount o where o.productdiscount.product.shop.id =:id and o.status = true and o.enddate > :date")
	List<Product_discount> findAllByShop(String id, Date date);
//	@Query("SELECT o.productdiscount.productSearch FROM Product_discount o where o.productdiscount.product.shop.id=?1")
	
//	Page<ProductSearch> getProductSaleByIdShop(String id, PageRequest pageRequest);

}
