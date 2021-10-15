package com.ede.edeproductservice.restcontroller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edeproductservice.service.ProductService;
import com.ede.edeproductservice.service.Product_option_service;

@RestController
@RequestMapping("/ede-product")
public class DeleteProductRestController {
	
	@Autowired
	ProductService service;
	
	@Autowired
	Product_option_service productOptionService;
	
	@DeleteMapping("/user/product/delete/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id")String id){
		try {
			return ResponseEntity.ok(productOptionService.deleteProductByID(id));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
		
	}

}