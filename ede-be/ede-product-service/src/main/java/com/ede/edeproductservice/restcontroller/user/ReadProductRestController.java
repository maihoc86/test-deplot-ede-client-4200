package com.ede.edeproductservice.restcontroller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.service.ProductService;

@RestController
@RequestMapping("/ede-product")
public class ReadProductRestController {
	
	@Autowired
	ProductService service;
	
	@GetMapping("/view/getAllProduct")
	public List<Product> getAllProduct() {
		return service.findAll();
	}
	
}