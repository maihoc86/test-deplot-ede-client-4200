package com.ede.edeproductservice.restcontroller.user;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.entity.Shop;
import com.ede.edeproductservice.service.ProductService;
import com.ede.edeproductservice.service.Product_brand_service;
import com.ede.edeproductservice.service.Product_child_category_service;
import com.ede.edeproductservice.service.ShopService;

@RestController
@RequestMapping("/ede-product")
public class CreateProductShopRestController {

	@Autowired
	ProductService service;

	@Autowired
	Product_brand_service brandService;

	@Autowired
	Product_child_category_service category_service;

	@Autowired
	ShopService shopService;

	@SuppressWarnings("rawtypes")
	@PostMapping("/create/product-shop")
	public ResponseEntity addProductAndSell(@RequestBody Product product) {
		UUID uuid = UUID.randomUUID();
		product.setId(uuid.toString());
		/************************/
		Shop find = shopService.findById("0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333"); // điền id shop vào ** đây là data mẫu
		product.setShop(find);
		return ResponseEntity.status(HttpStatus.OK).body(service.save(product));
	}
}
