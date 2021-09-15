package com.ede.edeproductservice.restcontroller.admin;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

/**
 * Create and add caterogy
 * @author hoc
 *
 */
@RestController
@RequestMapping("/ede-product/admin/")
@SuppressWarnings("rawtypes")
public class CreateCategoryRestController {

	@Autowired
	Product_parent_category_service product_parent_categoryService;

	@Autowired
	Product_child_parent_category_service product_child_parent_category_service;

	@Autowired
	Product_child_category_service product_child_category_service;

	/*thêm parent_category */
	@PostMapping("/create/parent_category")
	public ResponseEntity create_parent_category(@RequestBody Product_parent_category parent_category) {
		UUID uuid = UUID.randomUUID();
		System.out.println(uuid.toString());
		Optional<Product_parent_category> findCategory = product_parent_categoryService.findById(uuid.toString());
		if (findCategory.isPresent() && findCategory != null) {
			UUID uuid2 = UUID.randomUUID();
			parent_category.setId(uuid2.toString());
		} else {
			parent_category.setId(uuid.toString());
		}
		return ResponseEntity.status(HttpStatus.OK).body(product_parent_categoryService.save(parent_category));
	}
	
	/*thêm child_parent_category */
	@PostMapping("/create/child_parent_category")
	public ResponseEntity create_parent_child(@RequestBody Product_parent_child_category child_parent_category) {
		UUID uuid = UUID.randomUUID();
		Optional<Product_parent_child_category> findCategory = product_child_parent_category_service
				.findById(uuid.toString());
		if (findCategory.isPresent() && findCategory != null) {
			UUID uuid2 = UUID.randomUUID();
			child_parent_category.setId(uuid2.toString());
		} else {
			child_parent_category.setId(uuid.toString());
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(product_child_parent_category_service.save(child_parent_category));

	}

	/*thêm child_category */
	@PostMapping("/create/child_category")
	public ResponseEntity create_child(@RequestBody Product_child_category child_categories) {
		UUID uuid = UUID.randomUUID();
		Optional<Product_child_category> findCategory = product_child_category_service.findById(uuid.toString());
		if (findCategory.isPresent() && findCategory != null) {
			UUID uuid2 = UUID.randomUUID();
			child_categories.setId(uuid2.toString());
		} else {
			child_categories.setId(uuid.toString());
		}
		return ResponseEntity.status(HttpStatus.OK).body(product_child_category_service.save(child_categories));
	}
	
}
