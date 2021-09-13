/**
 * @author thái học
 *
 * 
 */
package com.ede.edeproductservice.restcontroller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edeproductservice.service.ProductService;

/**
 * @author thái học
 *
 * 
 */
@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/ede-product/admin")
public class ReadProductRestControllerAdmin {
	@Autowired
	ProductService service;

	@GetMapping("/getAll")
	public ResponseEntity getList() {
		return ResponseEntity.ok(service.listAll());
	}
}
