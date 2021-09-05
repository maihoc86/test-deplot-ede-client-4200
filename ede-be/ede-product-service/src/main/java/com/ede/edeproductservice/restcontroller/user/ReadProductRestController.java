package com.ede.edeproductservice.restcontroller.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.ede.edeproductservice.entity.extend.ProductSearch;
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
@RefreshScope
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
	public ResponseEntity<?> getProductByID(@PathVariable("id") String id) {
		if (product_option_service.findById(id).getIs_delete())
			return ResponseEntity.badRequest().build();
		return ResponseEntity.ok(product_option_service.findProductById(id));
	}

	@GetMapping("/view/getproductoption/{id}")
	public Product_option getProductOptionByIDProduct(@PathVariable("id") String id) {
		return product_option_service.findById(id);
	}

	@GetMapping("/view/getAllProduct")
	public Page<Product> getAllProduct(@RequestParam("page") Optional<Integer> page) {
		return service.listAll(PageRequest.of(page.orElse(0), 5));
	}

	@GetMapping("/view/getAllProductOption")
	public ResponseEntity<?> getAllProductOption(@RequestParam(name = "keyword") String keyword,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		Shop shop = new Shop();
		try {
			shop = auservice.getShopLogin(req.getHeader("Authorization"));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		Page<Product_option> pages = product_option_service.finAllByShop(keyword, shop, PageRequest.of(page, size));
		// List<Product_option>listProduct = product_option_service.finByShop(shop);
		System.err.println("listProduct size : " + pages.getSize());
		System.err.println(" size nè : " + size);
		System.err.println(" page nè : " + page);
		System.err.println(" keySearch nè : " + keyword);
		return ResponseEntity.ok(pages);
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("/view/getAllProductOption/quantity0")
	public ResponseEntity getAllProductOptionQuantity0(@RequestParam(name = "keyword") String keyword,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		Shop shop = new Shop();
		try {
			shop = auservice.getShopLogin(req.getHeader("Authorization"));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		Page<Product_option> pages = product_option_service.findProductQuantity0Shop(keyword, shop,
				PageRequest.of(page, size));
		System.err.println(
				"listProduct enable = true & is_delete = false in getAllProductOption/quantity0 : " + pages.getSize());
		System.err.println(" size nè : " + size);
		System.err.println(" page nè : " + page);
		System.err.println(" page conten: " + pages.getSize());
		System.err.println(" keySearch nè : " + keyword);
		return ResponseEntity.ok(pages);
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("/view/getAllProductOption/enable")
	public ResponseEntity getAllProductOptionEnableTrue(@RequestParam(name = "keyword") String keyword,
			@RequestParam(name = "value") Boolean value, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		Shop shop = new Shop();
		try {
			shop = auservice.getShopLogin(req.getHeader("Authorization"));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}

		Page<Product_option> pages = product_option_service.findProductEnableShop(keyword, shop, value,
				PageRequest.of(page, size));
		System.err.println("listProduct enable = true & is_delete = false : " + pages.getSize());
		System.err.println(" size nè : " + size);
		System.err.println(" page nè : " + page);
		System.err.println(" page conten: " + pages.getSize());
		System.err.println(" keySearch nè : " + keyword);
		return ResponseEntity.ok(pages);
	}

	@GetMapping("/view/getproductoptionimage/{id}")
	public List<Product_option_image> getImage(@PathVariable("id") String id) {
		return productImageService.findImageByIdOption(id);
	}

	@GetMapping("/view/getAllproductDiscount")
	public List<Product_discount> getAllProductDiscount() {
		return product_discount_service.findAll();
	}

	@GetMapping("/view/getcatrgory/{id}")
	public Product_child_category getProduct_child_category(@PathVariable("id") String id) {
		return product_option_service.findChildCategoryById(id);
	}

	@GetMapping("/view/getparentchildcatrgory/{id}")
	public Product_parent_child_category getProduct_parent_child_category(@PathVariable("id") String id) {
		return child_category_service.findParent(id);
	}

	@GetMapping("/view/getparentcatrgory/{id}")
	public Product_parent_category getProduct_parent_category(@PathVariable("id") String id) {
		return child_parent_category_service.findParent(id);
	}

	@GetMapping("/view/gettag/{id}")
	public List<Product_tag> etTag(@PathVariable("id") String id) {
		return product_Tag_service.findTagByidProduct(id);
	}

	/**
	 * Tìm sản phẩm
	 * 
	 * @author Vinh
	 * @param keysearch từ khóa tìm kiếm
	 * @return Đối tượng page chứa các sản phẩm giống với từ khóa nhất
	 */
	@CrossOrigin(allowedHeaders = "*")
	@GetMapping("/view/get-products")
	public ResponseEntity<?> getProducts(@RequestParam(value = "search", required = false) Optional<String> keysearch,
			@RequestParam(value = "page", required = false) Optional<Integer> page) {
		Page<ProductSearch> result;
		int npage = page.orElse(1) - 1; // cover page to index page
		if (npage < 0)
			npage = 0;
		result = this.service.searchByKeysearch(keysearch.orElse(""), PageRequest.of(npage, 12));
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

	// TODO: NEED OPTIMIZE IF ELSE
	@SuppressWarnings("rawtypes")
	@GetMapping("/view/customer/shop/all/product/filter")
	public ResponseEntity getListProductShopFilterCategory(@RequestParam("category") Optional<String> category,
			@RequestParam("location") Optional<String> location, @RequestParam("brand") Optional<String> brand,
			@RequestParam("page") Optional<Integer> page) {
		String valueCategory = category.orElse("");
		String valueBrand = brand.orElse("");
		String valueLocation = location.orElse("");
		PageRequest pageRequest = PageRequest.of(page.orElse(0), 10);
		Shop shop = new Shop();
		Page<ProductSearch> listPage = null;
		String[] splitLocation = null;
		String[] splitBrand = null;
		List<String> locationList = null;
		List<String> brandList = null;
		if (location.isPresent()) {
			// SPLIT STRING AND ADD TO LIST
			splitLocation = valueLocation.split(",");
			locationList = new ArrayList<String>();
			for (int i = 0; i < splitLocation.length; i++) {
				locationList.add(splitLocation[i]);
			}
		}
		if (brand.isPresent()) {
			// SPLIT STRING AND ADD TO LIST
			splitBrand = valueBrand.split(",");
			brandList = new ArrayList<String>();
			for (int i = 0; i < splitBrand.length; i++) {
				brandList.add(splitBrand[i]);
			}
		}
		// CHECKLOGIN SHOP
		try {
			// TODO: LẤY ID SHOP TRÊN URL CHỨ KHÔNG LẤY THẰNG ĐÃ LOGIN
			shop = auservice.getShopLogin(req.getHeader("Authorization"));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		if (category.isPresent() && valueLocation.equals("") && valueBrand.equals("")) {
			// CATEGORY
			System.err.println(1);
			listPage = service.filterProductShopByCustomerCategory(valueCategory, shop.getId(), pageRequest);
		} else if (location.isPresent() && valueCategory.equals("") && valueBrand.equals("")) {
			// LOCATION
			System.err.println(2);
			listPage = service.filterProductShopByCustomerLocation(locationList, shop.getId(), pageRequest);
		} else if (brand.isPresent() && valueLocation.equals("") && valueCategory.equals("")) {
			// BRAND
			System.err.println(3 + valueBrand);
			listPage = service.filterProductShopByCustomerBrand(brandList, shop.getId(), pageRequest);
		} else if (category.isPresent() && location.isPresent() && valueBrand.equals("")) {
			// CATEGORY AND LOCATION
			System.err.println(4 + valueBrand);
			listPage = service.filterProductShopByCustomerLocationAndCategory(locationList, valueCategory, shop.getId(),
					pageRequest);
		} else if (location.isPresent() && brand.isPresent() && valueCategory.equals("")) {
			// LOCATION AND BRAND
			System.err.println(5);
			listPage = service.filterProductShopByCustomerLocationAndBrand(locationList, brandList, shop.getId(),
					pageRequest);
		} else if (category.isPresent() && brand.isPresent() && valueLocation.equals("")) {
			// CATEGORY AND BRAND
			System.err.println(6);
			listPage = service.filterProductShopByCustomerCategoryAndBrand(valueCategory, brandList, shop.getId(),
					pageRequest);
		} else {
			// ALL
			listPage = service.filterProductShopByCustomerLocationAndCategoryAndBrand(locationList, valueCategory,
					brandList, shop.getId(), pageRequest);
		}

		return ResponseEntity.ok(listPage);
	}

//	@SuppressWarnings("rawtypes")
//	@GetMapping("/view/customer/shop/all/product/filterLocation")
//	public ResponseEntity getListProductShopFilterLocation(@RequestParam("location") Optional<String> location,
//			@RequestParam("page") Optional<Integer> page) {
//
//		Shop shop = new Shop();
//		try {
//			shop = auservice.getShopLogin(req.getHeader("Authorization"));
//		} catch (Exception e) {
//			return ResponseEntity.notFound().build();
//		}
//		Page<ProductSearch> pageF = service.filterProductShopByCustomerLocation(locaList, shop.getId(),
//				PageRequest.of(page.orElse(0), 10));
//		return ResponseEntity.ok(pageF);
//	}

	/* GET CATEGORY SHOP */
	@SuppressWarnings("rawtypes")
	@GetMapping("/view/customer/shop/all/category")
	public ResponseEntity getListCategoryShop() {
		Shop shop = new Shop();
		try {
			shop = auservice.getShopLogin(req.getHeader("Authorization"));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(child_category_service.findAllByShop(shop.getId()));
	}

	/* ALL PRODUCT VIEW SHOP BY CUSTOMER */

	@SuppressWarnings("rawtypes")
	@GetMapping("/view/customer/shop/all/product")
	public ResponseEntity getAllListProductByCustomer(@RequestParam("page") Optional<Integer> page) {
		Shop shop = new Shop();
		try {
			shop = auservice.getShopLogin(req.getHeader("Authorization"));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		System.err.println(shop.getId());
		// TODO: Sửa 1 page 20 item, mỗi item product bắt buộc phải có option
		Page<ProductSearch> pageF = service.listAllProductShopByCustomer(shop.getId(),
				PageRequest.of(page.orElse(0), 10));
		return ResponseEntity.ok(pageF);
	}

	/* ALL PRODUCT DISCOUNT VIEW SHOP BY CUSTOMER */
	@SuppressWarnings("rawtypes")
	@GetMapping("/view/customer/shop/all/product/discount")
	public ResponseEntity getAllListProductDiscountByCustomer() {

		Shop shop = new Shop();
		try {
			shop = auservice.getShopLogin(req.getHeader("Authorization"));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		// TODO: Sửa 1 page 20 item, mỗi item product bắt buộc phải có option
		List<Product_discount> pageF = product_discount_service.findByIdProduct(shop.getId());
		return ResponseEntity.ok(pageF);
	}
}
