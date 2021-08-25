package com.ede.edeproductservice.restcontroller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edeproductservice.service.ProductService;

@RestController
@RequestMapping("/ede-product")
public class DeleteProductRestController {
	
	@Autowired
	ProductService service;

}