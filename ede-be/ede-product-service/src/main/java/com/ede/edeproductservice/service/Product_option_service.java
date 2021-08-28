package com.ede.edeproductservice.service;
import java.util.List;

import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.entity.Product_child_category;
import com.ede.edeproductservice.entity.Product_option;
import com.ede.edeproductservice.entity.Shop;

public interface Product_option_service {

	Product_option save(Product_option product_option);

	List<Product_option> findAll();

	List<Product_option> findByProductEnable(Boolean value);

	List<Product_option> findProductQuantity0();

	List<Product_option> filterProductShopByCustomerAND(String location, String category, String brand);
	List<Product_option> filterProductShopByCustomerOR(String location, String category, String brand);

	Product_option findById(String id);

	Product_option findByIdbyProduct(String id);

	List<Product_option> finByShop(Shop shop);

	Product_option deleteProductByID(String id);

	Product findProductById(String id);

	Product_child_category findChildCategoryById(String id);
}
