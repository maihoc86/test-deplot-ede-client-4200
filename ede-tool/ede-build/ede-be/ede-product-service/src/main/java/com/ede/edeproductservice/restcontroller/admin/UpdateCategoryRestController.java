package com.ede.edeproductservice.restcontroller.admin;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

/**
 * Update or Add (if not exists) Category
 * @author vinh
 *
 */
@RestController
@RequestMapping("/ede-product")
public class UpdateCategoryRestController {

	@Autowired
	Product_parent_category_service product_parent_categoryService;

	@Autowired
	Product_child_parent_category_service product_child_parent_category_service;

	@Autowired
	Product_child_category_service product_child_category_service;

	/**
	 * Update parent_category 
	 * @author vinh
	 */
	@PutMapping("update/parent-category")
	ResponseEntity<Product_parent_category> updateParentCategory(@RequestBody Product_parent_category parentCategory){
		if (!this.product_parent_categoryService.existsById(parentCategory.getId())) {
			parentCategory = this.product_parent_categoryService.save(parentCategory);
			//FIXME Chưa thêm link đối chiếu khi thêm mới category thay vì cập nhật
			return ResponseEntity.created(URI.create("")).body(parentCategory);
		}
		return ResponseEntity.ok(this.product_parent_categoryService.update(parentCategory));
	}

	/**
	 * Update parent child category
	 * @author vinh
	 */
	@PutMapping("update/parent-child-category")
	ResponseEntity<Product_parent_child_category> updateParentChildCategory(@RequestBody Product_parent_child_category parentChildCategory){
		if (!this.product_child_parent_category_service.existsById(parentChildCategory.getId())) {
			parentChildCategory = this.product_child_parent_category_service.save(parentChildCategory);
			//FIXME Chưa thêm link đối chiếu khi thêm mới category thay vì cập nhật
			return ResponseEntity.created(URI.create("")).body(parentChildCategory);
		}
		return ResponseEntity.ok(this.product_child_parent_category_service.update(parentChildCategory));
	}

	/**
	 * Update child category 
	 * @author vinh
	 */
	@PutMapping("update/child-category")
	ResponseEntity<Product_child_category> updateChildCategory(@RequestBody Product_child_category childCategory){
		if (!this.product_child_category_service.existsById(childCategory.getId())) {
			childCategory = this.product_child_category_service.save(childCategory);
			//FIXME Chưa thêm link đối chiếu khi thêm mới category thay vì cập nhật
			return ResponseEntity.created(URI.create("")).body(childCategory);
		}
		return ResponseEntity.ok(this.product_child_category_service.update(childCategory));
	}
	
}
