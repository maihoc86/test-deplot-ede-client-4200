package com.ede.edeproductservice.implement;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ede.edeproductservice.dao.Product_discount_Dao;
import com.ede.edeproductservice.entity.Product_discount;
import com.ede.edeproductservice.entity.extend.ProductSearch;
import com.ede.edeproductservice.service.Product_discount_service;

@Service
public class Product_discount_impl implements Product_discount_service {

	@Autowired
	Product_discount_Dao dao;

	@Override
	public Product_discount save(Product_discount product_discount) {
		return dao.save(product_discount);
	}

	@Override
	public List<Product_discount> findAll() {
		return dao.findAll();
	}

	/**
	 * @author thái học
	 *
	 * 
	 */
	@Override
	public List<Product_discount> findByIdProduct(String id, Date date) {
		return dao.findAllByShop(id, date);
	}

	@Override
	public Page<ProductSearch> getProductSaleByIdShop(String id, PageRequest pageRequest) {
		return null;
	}

}
