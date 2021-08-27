package com.ede.edeproductservice.restcontroller.user;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.entity.Product_brand;
import com.ede.edeproductservice.entity.Product_child_category;
import com.ede.edeproductservice.entity.Product_discount;
import com.ede.edeproductservice.entity.Product_option;
import com.ede.edeproductservice.entity.Product_option_image;
import com.ede.edeproductservice.entity.Product_parent_category;
import com.ede.edeproductservice.entity.Product_parent_child_category;
import com.ede.edeproductservice.entity.Product_tag;
import com.ede.edeproductservice.entity.Shop;
import com.ede.edeproductservice.service.Auth_Service;
import com.ede.edeproductservice.service.ProductService;
import com.ede.edeproductservice.service.Product_Tag_service;
import com.ede.edeproductservice.service.Product_brand_service;
import com.ede.edeproductservice.service.Product_child_category_service;
import com.ede.edeproductservice.service.Product_child_parent_category_service;
import com.ede.edeproductservice.service.Product_discount_service;
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
	Product_Tag_service product_Tag_service;

	@Autowired
	Product_option_service product_option_service;

	@Autowired
	Product_discount_service product_discount_service;
	
	@Autowired
	ShopService shopService;
	
	@Autowired
	Auth_Service auservice;
	
	@Autowired
	HttpServletRequest req;


	@GetMapping("/view/getproductbyid/{id}")
	public Product getProductByID(@PathVariable("id") String id) {
		return service.findById(id);
	}

	@GetMapping("/view/getproductoption/{id}")
	public Product_option getProductOptionByIDProduct(@PathVariable("id") String id) {
		return product_option_service.findByIdbyProduct(id);
	}
	
	@GetMapping("/view/getAllProduct")
	public List<Product> getAllProduct() {
		return service.findAll();
	}
	
	
	
	@GetMapping("/view/getAllProductOption")
	public ResponseEntity<?> getAllProductOption() {
		Shop shop = new Shop();
		try {
			 shop = auservice.getShopLogin(req.getHeader("Authorization"));
		} catch (Exception e) {
		return ResponseEntity.notFound().build();
		}
		List<Product_option>listProduct = product_option_service.finByShop(shop);
		System.err.println("listProduct: "+listProduct.size());
		return ResponseEntity.ok(listProduct);
		
	}
	@GetMapping("/view/getAllproductDiscount")
	public List<Product_discount> getAllProductDiscount() {
		return product_discount_service.findAll();
	}
	@GetMapping("/view/getcatrgory/{id}")
	public Product_child_category getProduct_child_category(@PathVariable("id")String id) {
		return service.findCategorybyIDProduct(id);
	}
	@GetMapping("/view/getparentchildcatrgory/{id}")
	public Product_parent_child_category getProduct_parent_child_category(@PathVariable("id")String id) {
		return child_category_service.findParent(id);
	}
	@GetMapping("/view/getparentcatrgory/{id}")
	public Product_parent_category getProduct_parent_category(@PathVariable("id")String id) {
		return child_parent_category_service.findParent(id);
	}
	@GetMapping("/view/gettag/{id}")
	public List<Product_tag>etTag(@PathVariable("id")String id) {
		return product_Tag_service.findTagByidProduct(id);
	}
	/**
	 * Tìm sản phẩm
	 * 
	 * @author Vinh
	 * @param keysearch từ khóa tìm kiếm
	 * @return Đối tượng page chứa các sản phẩm giống với từ khóa nhất
	 */
	@GetMapping("/view/get-products")
	public ResponseEntity<Page<Product>> getProducts(
			@RequestParam(value = "search", required = false) Optional<String> keysearch,
			@RequestParam(value = "page", required = false) Optional<Integer> page
	) {
		Page<Product> result;
		int npage = page.orElse(1) - 1; //cover page to index page
		if (npage < 0) npage = 0;
		result = this.service.searchByKeysearch(keysearch.orElse(""), PageRequest.of(npage, 1));
		return ResponseEntity.ok(result);
	}

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
	public List<Product_option_image> getListOption_images() {
		return productImageService.findAll();
	}

	@GetMapping("/view/list_product_tag")
	public List<Product_tag> getListProduct_Tag() {
		return product_Tag_service.findAll();
	}
	
	@GetMapping("/view/list_product_discount")
	public List<Product_discount> getListProduct_discount() {
		return product_discount_service.findAll();
	}

	// TODO: Filter product shop by customer
	@PostMapping("/view/customer/shop/list_product/filter")
	public List<Product_option> getList(@RequestParam Optional<String> location,
			@RequestParam Optional<String> category, @RequestParam Optional<String> brand) {
		String valueLocate = location.orElse("");
		String valueCate = category.orElse("");
		String valueBrand = brand.orElse("");
		if (!valueLocate.equals("") && !valueBrand.equals("") && !valueCate.equals("")) {
			return product_option_service.filterProductShopByCustomerAND(valueLocate, valueCate, valueBrand);
		} else {
			return product_option_service.filterProductShopByCustomerOR(valueLocate, valueCate, valueBrand);
		}
	}
}
