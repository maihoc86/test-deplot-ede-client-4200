package com.ede.edeproductservice.restcontroller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.entity.Product_brand;
import com.ede.edeproductservice.entity.Product_child_category;
import com.ede.edeproductservice.entity.Product_option_image;
import com.ede.edeproductservice.entity.Product_parent_category;
import com.ede.edeproductservice.entity.Product_parent_child_category;
import com.ede.edeproductservice.service.ProductService;
import com.ede.edeproductservice.service.Product_brand_service;
import com.ede.edeproductservice.service.Product_child_category_service;
import com.ede.edeproductservice.service.Product_child_parent_category_service;
import com.ede.edeproductservice.service.Product_option_image_service;
import com.ede.edeproductservice.service.Product_option_service;
import com.ede.edeproductservice.service.Product_parent_category_service;
import com.ede.edeproductservice.service.ShopService;

@RestController
@RequestMapping("/ede-product")
public class ReadProductRestController {

	@Autowired
	ProductService service;

	@Autowired
	Product_brand_service brandService;

	@Autowired
	Product_child_category_service child_category_service;

	@Autowired
	Product_parent_category_service parent_category_service;

	@Autowired
	Product_child_parent_category_service child_parent_category_service;

	@Autowired
	Product_option_image_service productImageService;
	
	@Autowired
	ShopService shopService;

	@GetMapping("/view/getAllProduct")
	public List<Product> getAllProduct() {
		return service.findAll();
	}

//	@Autowired
//	private EntityManager en;

//	@GetMapping("/view/get-products/{keysearch}")
//	public ResponseEntity<Object> getProducts(@PathVariable("keysearch") String keysearch) {
//		Page<Product> result = this.service.searchByKeysearch(keysearch, PageRequest.of(0, 10));
//// TODO Đánh dấu ở đây để mai code tiếp, chổ này cần phải nhanh chóng fix
////		List<Object> result = en.createQuery("SELECT p FROM ProductSearch p ORDER BY function('dbo.string_distance', p.keysearch, '"+keysearch+"') ASC")
////				.getResultList();
//		
//		System.err.println(result + "-----------");
//		return ResponseEntity.ok(result);
//	}
//	
	@GetMapping("/view/listBrand")
	public List<Product_brand> getBrands() {
		return brandService.findAll();
	}

	@GetMapping("/view/list_parent_category")
	public List<Product_parent_category> getParentCategories() {
		return parent_category_service.findAll();
	}

	@GetMapping("/view/list_parent_child_category")
	public List<Product_parent_child_category> getParentChild_categories() {
		return child_parent_category_service.findAll();
	}

	@GetMapping("/view/list_child_category")
	public List<Product_child_category> getCategories() {
		return child_category_service.findAll();
	}

	@GetMapping("/view/list_parent_child_category/{id}")
	public List<Product_parent_child_category> getParentChild_categories_byIdParent(@PathVariable("id") String id) {
		System.out.println(id);
		List<Product_parent_child_category> findCategory = child_parent_category_service.findByIdParent(id);
		System.out.println(findCategory);
		return child_parent_category_service.findByIdParent(id);
	}

	@GetMapping("/view/list_child_category/{id}")
	public List<Product_child_category> getChild_categories_byIdParentChild(@PathVariable("id") String id) {
		System.out.println(id);
		List<Product_parent_child_category> findCategory = child_parent_category_service.findByIdParent(id);
		System.out.println(findCategory);
		return child_category_service.findByIdParentChild(id);
	}
	
	@GetMapping("/view/list_option_image")
	public List<Product_option_image> getListOption_images(){
		return productImageService.findAll();
	}
}