package com.ede.edeproductservice.restcontroller.admin;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	

	/*************************************************/
	@GetMapping("/delete/parent_category/{id}")
	public Product_parent_category deleteParent_category(@PathVariable("id") String id) {
		return product_parent_categoryService.deleteParent(id);
	}
	@GetMapping("/delete/parent_child_category/{id}")
	public Product_parent_child_category deleteparent_child_category(@PathVariable("id") String id) {
		return product_child_parent_category_service.deleteParent_Child(id);
	}
	@GetMapping("/delete/child_category/{id}")
	public Product_child_category deletechild_category(@PathVariable("id") String id) {
		return product_child_category_service.deleteChild(id);
	}
	
	
	//<<<<<<<<<<<<<<<<<<< update category start: vinh
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
	//>>>>>>>>>>>>>>>>>>> update category end: vinh
	
}
