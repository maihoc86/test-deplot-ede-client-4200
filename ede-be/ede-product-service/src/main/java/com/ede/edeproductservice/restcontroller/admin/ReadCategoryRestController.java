package com.ede.edeproductservice.restcontroller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
 * Read (get, search) category
 * @author hoc
 * @author thanh
 *
 */
@RestController
@RequestMapping("/ede-product")
public class ReadCategoryRestController {

	@Autowired
	Product_parent_category_service product_parent_categoryService;

	@Autowired
	Product_child_parent_category_service product_child_parent_category_service;

	@Autowired
	Product_child_category_service product_child_category_service;

	/**
	 * Xem category
	 * @author hoc
	 */
	@GetMapping("/view/parent_category")
	public List<Product_parent_category> view_parent() {
		return product_parent_categoryService.findAll();
	}

	@GetMapping("/view/child_parent_category")
	public List<Product_parent_child_category> view_child_parent() {
		return product_child_parent_category_service.findAll();
	}

	@GetMapping("/view/child_category")
	public List<Product_child_category> view_child() {
		return product_child_category_service.findAll();
	}

	/**
	 * Search 
	 * 
	 * @author thanh
	 * @see
	 */
	@GetMapping("/search/parent_category/{name}")
	public List<Product_parent_category>searchP(@PathVariable("name") String name){
		System.out.println("name Parent category: "+name);
		return product_parent_categoryService.findByNameContaining(name);
	}
	
	@GetMapping("/search/parent_child_category/{name}")
	public List<Product_parent_child_category>searchPC(@PathVariable("name") String name){
		System.out.println("name parent child category: "+name);
		return product_child_parent_category_service.findByNameContaining(name);
	}
	
	@GetMapping("/search/child_category/{name}")
	public List<Product_child_category>searchC(@PathVariable("name") String name){
		System.out.println("name child category: "+name);
		return product_child_category_service.findByNameContaining(name);
	}
	
}
