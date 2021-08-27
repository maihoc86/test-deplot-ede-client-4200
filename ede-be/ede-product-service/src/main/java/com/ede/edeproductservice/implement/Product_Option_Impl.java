package com.ede.edeproductservice.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ede.edeproductservice.dao.Product_option_Dao;
import com.ede.edeproductservice.entity.Product_option;
import com.ede.edeproductservice.entity.Shop;
import com.ede.edeproductservice.service.Product_option_service;

@Service
public class Product_Option_Impl implements Product_option_service {

	@Autowired
	Product_option_Dao dao;

	@Override
	public Product_option save(Product_option product_option) {
		return dao.save(product_option);
	}

	@Override
	public List<Product_option> findAll() {
		return dao.findAll();
	}

	@Override
	public List<Product_option> findByProductEnable(Boolean value) {
		return dao.findByProductEnable(value);
	}

	@Override
	public List<Product_option> findProductQuantity0() {
		return dao.findProductQuantity0();
	}

	@Override
	public List<Product_option> filterProductShopByCustomerAND(String location, String category, String brand) {
		return dao.filterProductShopByCustomerAND(location, category,  brand);
	}
	@Override
	public List<Product_option> filterProductShopByCustomerOR(String location, String category, String brand) {
		return dao.filterProductShopByCustomerOR(location, category,  brand);
	}

	@Override
	public Product_option findById(String id) {
		return dao.findById(id).get();
	}

	@Override
	public Product_option findByIdbyProduct(String id) {
		
		return dao.findByIdbyProduct(id);
	}

	@Override
	public List<Product_option> finByShop(Shop shop) {
		// TODO Auto-generated method stub
		return dao.finByShop(shop);
	}

	@Override
	public Product_option deleteProductByID(String id) {
		Product_option product = dao.findByIdbyProduct(id);
		product.setIs_delete(true);
		return dao.save(product);
	}
}
