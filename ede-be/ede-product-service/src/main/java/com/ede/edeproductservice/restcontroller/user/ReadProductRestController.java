package com.ede.edeproductservice.restcontroller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.entity.Product_brand;
import com.ede.edeproductservice.entity.Product_child_category;
import com.ede.edeproductservice.service.ProductService;
import com.ede.edeproductservice.service.Product_brand_service;
import com.ede.edeproductservice.service.Product_child_category_service;
import com.ede.edeproductservice.service.ShopService;

@RestController
@RequestMapping("/ede-product")
public class ReadProductRestController {
	
	@Autowired
	ProductService service;

	@Autowired
	Product_brand_service brandService;

	@Autowired
	Product_child_category_service category_service;

	@Autowired
	ShopService shopService;
	
	@GetMapping("/view/getAllProduct")
	public List<Product> getAllProduct() {
		return service.findAll();
	}
	@GetMapping("/view/listBrand")
	public List<Product_brand> getBrands() {
		return brandService.findAll();
	}

	@GetMapping("/view/listCategories")
	public List<Product_child_category> getCategories() {
		return category_service.findAll();
	}
}