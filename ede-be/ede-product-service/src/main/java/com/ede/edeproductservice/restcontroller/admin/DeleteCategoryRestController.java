package com.ede.edeproductservice.restcontroller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edeproductservice.entity.Product_child_category;
import com.ede.edeproductservice.entity.Product_parent_category;
import com.ede.edeproductservice.entity.Product_parent_child_category;
import com.ede.edeproductservice.service.Product_child_category_service;
import com.ede.edeproductservice.service.Product_child_parent_category_service;
import com.ede.edeproductservice.service.Product_parent_category_service;

/**
 * Delete category
 * @author viet
 *
 */
@RestController
@RequestMapping("/ede-product")
public class DeleteCategoryRestController {

	@Autowired
	Product_parent_category_service product_parent_categoryService;

	@Autowired
	Product_child_parent_category_service product_child_parent_category_service;

	@Autowired
	Product_child_category_service product_child_category_service;

	@DeleteMapping("/delete/parent_category/{id}")
	public Product_parent_category deleteParent_category(@PathVariable("id") String id) {
		return product_parent_categoryService.deleteParent(id);
	}
	
	@DeleteMapping("/delete/parent_child_category/{id}")
	public Product_parent_child_category deleteparent_child_category(@PathVariable("id") String id) {
		return product_child_parent_category_service.deleteParent_Child(id);
	}
	
	@DeleteMapping("/delete/child_category/{id}")
	public Product_child_category deletechild_category(@PathVariable("id") String id) {
		return product_child_category_service.deleteChild(id);
	}
	
}
