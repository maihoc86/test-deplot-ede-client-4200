/**
 * @author thái học
 *
 * 
 */
package com.ede.edeproductservice.restcontroller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edeproductservice.entity.Product_option;
import com.ede.edeproductservice.service.ProductService;
import com.ede.edeproductservice.service.Product_option_service;

/**
 * @author thái học
 *
 * 
 */
@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/ede-product")
public class ReadProductRestControllerAdmin {
	@Autowired
	ProductService service;
	@Autowired
	Product_option_service product_option_service;

	@GetMapping("/admin/getAll")
	public ResponseEntity getList(@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		Page<Product_option> pages = product_option_service.listAll(PageRequest.of(page, size));
		return ResponseEntity.ok(pages);
	}
}
