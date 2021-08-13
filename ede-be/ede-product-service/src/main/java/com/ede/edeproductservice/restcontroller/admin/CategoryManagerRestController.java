package com.ede.edeproductservice.restcontroller.admin;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edeproductservice.entity.Product_child_category;
import com.ede.edeproductservice.entity.Product_parent_category;
import com.ede.edeproductservice.entity.Product_parent_child_category;
import com.ede.edeproductservice.service.Product_child_category_service;
import com.ede.edeproductservice.service.Product_child_parent_category_service;
import com.ede.edeproductservice.service.Product_parent_category_service;

@RestController
@RequestMapping("/ede-product")
public class CategoryManagerRestController {

	@Autowired
	Product_parent_category_service product_parent_categoryService;

	@Autowired
	Product_child_parent_category_service product_child_parent_category_service;

	@Autowired
	Product_child_category_service product_child_category_service;

	/* Xem và thêm parent_category */
	@SuppressWarnings("rawtypes")
	@PostMapping("/create/parent_category")
	public ResponseEntity create_parent_category(@RequestBody Product_parent_category parent_category) {
		UUID uuid = UUID.randomUUID();
		parent_category.setId(uuid.toString());
		return ResponseEntity.status(HttpStatus.OK).body(product_parent_categoryService.save(parent_category));
	}

	@GetMapping("/view/parent_category")
	public List<Product_parent_category> view_parent() {
		return product_parent_categoryService.findAll();
	}

	/*************************************************/

	/* Xem và thêm child_parent_category */
	@SuppressWarnings("rawtypes")
	@PostMapping("/create/child_parent_category")
	public ResponseEntity create_parent_child(@RequestBody List<Product_parent_child_category> child_parent_category) {
		for (Product_parent_child_category item : child_parent_category) {
			UUID uuid = UUID.randomUUID();
			item.setId(uuid.toString());
			product_child_parent_category_service.save(item);
		}
		return null; // save đc list nhưng chưa nghĩ ra return để làm sau
	}

	@GetMapping("/view/child_parent_category")
	public List<Product_parent_child_category> view_child_parent() {

		return product_child_parent_category_service.findAll();
	}
	/*************************************************/
	
	/* Xem và thêm child_category */
	@SuppressWarnings("rawtypes")
	@PostMapping("/create/child_category")
	public ResponseEntity create_child(@RequestBody List<Product_child_category> child_categories) {
		for (Product_child_category item : child_categories) {
			UUID uuid = UUID.randomUUID();
			item.setId(uuid.toString());
			product_child_category_service.save(item);
		}
		return null; // save đc list nhưng chưa nghĩ ra return để làm sau
	}

	@GetMapping("/view/child_parent_category")
	public List<Product_child_category> view_child() {

		return product_child_category_service.findAll();
	}
	/*************************************************/

}
