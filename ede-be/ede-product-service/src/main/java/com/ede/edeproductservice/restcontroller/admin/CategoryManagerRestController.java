package com.ede.edeproductservice.restcontroller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import javax.ws.rs.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@GetMapping("/view/parent_category")
	public List<Product_parent_category> view_parent() {
		return product_parent_categoryService.findAll();
	}

	/*************************************************/

	/* Xem và thêm child_parent_category */
	@SuppressWarnings("rawtypes")
	@PostMapping("/create/child_parent_category")
	public ResponseEntity create_parent_child(@RequestBody Product_parent_child_category child_parent_category) {
		System.out.println(child_parent_category.getParent_category());
		System.out.println(child_parent_category);
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

	@GetMapping("/view/child_parent_category")
	public List<Product_parent_child_category> view_child_parent() {
		return product_child_parent_category_service.findAll();
	}

	/*************************************************/

	/* Xem và thêm child_category */
	@SuppressWarnings("rawtypes")
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

	@GetMapping("/view/child_category")
	public List<Product_child_category> view_child() {
		return product_child_category_service.findAll();
	}

	/*************************************************/
	@GetMapping("/delete/child_parent_category/{id}")
	public Product_parent_category deleteChild_parent_category(@PathVariable("id") String id) {
		return product_parent_categoryService.deleteParent(id);
	}
}
