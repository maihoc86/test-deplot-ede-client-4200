package com.ede.edeproductservice.restcontroller.user;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edeproductservice.ResponseHandler;
import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.entity.Product_brand;
import com.ede.edeproductservice.entity.Product_child_category;
import com.ede.edeproductservice.entity.Product_discount;
import com.ede.edeproductservice.entity.Product_meta;
import com.ede.edeproductservice.entity.Product_option;
import com.ede.edeproductservice.entity.Product_option_image;
import com.ede.edeproductservice.entity.Product_parent_category;
import com.ede.edeproductservice.entity.Product_parent_child_category;
import com.ede.edeproductservice.entity.Product_tag;
import com.ede.edeproductservice.entity.Shop;
import com.ede.edeproductservice.entity.extend.ProductSearch;
import com.ede.edeproductservice.service.Auth_Service;
import com.ede.edeproductservice.service.Order_detail_service;
import com.ede.edeproductservice.service.ProductService;
import com.ede.edeproductservice.service.Product_Tag_search_service;
import com.ede.edeproductservice.service.Product_Tag_service;
import com.ede.edeproductservice.service.Product_brand_service;
import com.ede.edeproductservice.service.Product_child_category_service;
import com.ede.edeproductservice.service.Product_child_parent_category_service;
import com.ede.edeproductservice.service.Product_discount_service;
import com.ede.edeproductservice.service.Product_meta_service;
import com.ede.edeproductservice.service.Product_option_image_service;
import com.ede.edeproductservice.service.Product_option_service;
import com.ede.edeproductservice.service.Product_parent_category_service;
import com.ede.edeproductservice.service.ShopService;

@RestController
@RefreshScope
@RequestMapping("/ede-product")
@SuppressWarnings("rawtypes")
public class ReadProductRestController {

	/**
	 * this is ProdutService
	 */
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
	Product_Tag_search_service product_tag_search_service;

	@Autowired
	Product_option_service product_option_service;

	@Autowired
	Product_discount_service product_discount_service;

	@Autowired
	Product_meta_service product_meta_service;

	@Autowired
	ShopService shopService;

	@Autowired
	Auth_Service auservice;

	@Autowired
	HttpServletRequest req;

	@Autowired
	Order_detail_service order_detail_service;

	@GetMapping("/view/getproductbyid/{id}")
	public ResponseEntity<?> getProductByID(@PathVariable("id") String id) {
		if (product_option_service.findById(id).getIs_delete())
			return ResponseEntity.badRequest().build();
		return ResponseEntity.ok(product_option_service.findProductById(id));
	}

	@GetMapping("/view/getProduct/{id}")
	public ResponseEntity getProduct(@PathVariable("id") String id) {
		if (service.findById(id).getDeleted())
			return ResponseEntity.badRequest().build();
		return ResponseEntity.ok(service.findById(id));
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
		return ResponseEntity.ok(pages);
	}

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
		return ResponseEntity.ok(pages);
	}

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
		return ResponseEntity.ok(pages);
	}

	@GetMapping("/view/getproductoptionimage/{id}")
	public List<Product_option_image> getImage(@PathVariable("id") String id) {
		return productImageService.findImageByIdOption(id);
	}

	@GetMapping("/view/getProductOptionImage/only/{id}")
	public Product_option_image getImageOnly(@PathVariable("id") String id) {
		return productImageService.findImageByIdOption(id).get(0);
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
	@GetMapping("/view/get-products")
	public ResponseEntity<Page<ProductSearch>> getProducts(
			@RequestParam(value = "search", required = false) Optional<String> keysearch,
			@RequestParam(value = "page", required = false) Optional<Integer> page) {
		Page<ProductSearch> result;
		int npage = page.orElse(1) - 1; // cover page to index page
		if (npage < 0)
			npage = 0;
		result = this.service.searchByKeysearch(keysearch.orElse(""), PageRequest.of(npage, 12));
		return ResponseEntity.ok(result);
	}

	/**
	 * Lấy 1 sản phẩm (trả về entity Product Search) <strong>Phục vụ quá trình xem
	 * chi tiết sản phẩm</strong>
	 * 
	 * @author Vinh
	 * @param id Id của sản phẩm
	 * @return Một sản phẩm search
	 */
	@GetMapping("/view/get-product-search/{id}")
	public ResponseEntity<ProductSearch> getProducts(@PathVariable("id") String id) {
		ProductSearch productSearch = this.service.findByProductSearchId(id);
		return ResponseEntity.ok(productSearch);
	}

	/* Lấy ra tất cả brand */
	@GetMapping("/view/listBrand")
	public List<Product_brand> getBrands() {
		return brandService.findAll();
	}

	@GetMapping("/view/listBrandByShop")
	public List<Product_brand> getBrands(@RequestParam("idShop") Optional<String> idShop) {
		String valueIdShop = idShop.orElse("");
		return service.selectAllBrandInShop(valueIdShop);
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
		return child_parent_category_service.findByIdParent(id);
	}

	@GetMapping("/view/list_child_category/{id}")
	public List<Product_child_category> getChild_categories_byIdParentChild(@PathVariable("id") String id) {
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

	@GetMapping("/view/customer/shop/all/product/filter")
	public ResponseEntity getListProductShopFilterCategory(@RequestParam("idShop") Optional<String> idShop,
			@RequestParam("category") Optional<String> category, @RequestParam("location") Optional<String> location,
			@RequestParam("brand") Optional<String> brand, @RequestParam("page") Optional<Integer> page,
			@RequestParam("new") Optional<Boolean> selling) {
		String valueCategory = category.orElse("");
		String valueBrand = brand.orElse("");
		String valueLocation = location.orElse("");
		String valueIdShop = idShop.orElse("");
		Optional<Shop> shop = shopService.findByIdOptional(idShop.orElse(""));
		if (shop.isEmpty()) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Của hàng không tồn tại", "idShop",
					null);
		}
		PageRequest pageRequest = PageRequest.of(page.orElse(0), 10);
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
		if (category.isPresent() && valueLocation.equals("") && valueBrand.equals("")) {
			// CATEGORY
			listPage = service.filterProductShopByCustomerCategory(valueCategory, valueIdShop, pageRequest);

		} else if (location.isPresent() && valueCategory.equals("") && valueBrand.equals("")) {
			// LOCATION
			listPage = service.filterProductShopByCustomerLocation(locationList, valueIdShop, pageRequest);
		} else if (brand.isPresent() && valueLocation.equals("") && valueCategory.equals("")) {
			// BRAND
			listPage = service.filterProductShopByCustomerBrand(brandList, valueIdShop, pageRequest);
		} else if (category.isPresent() && location.isPresent() && valueBrand.equals("")) {
			// CATEGORY AND LOCATION
			listPage = service.filterProductShopByCustomerLocationAndCategory(locationList, valueCategory, valueIdShop,
					pageRequest);
		} else if (location.isPresent() && brand.isPresent() && valueCategory.equals("")) {
			// LOCATION AND BRAND
			listPage = service.filterProductShopByCustomerLocationAndBrand(locationList, brandList, valueIdShop,
					pageRequest);
		} else if (category.isPresent() && brand.isPresent() && valueLocation.equals("")) {
			// CATEGORY AND BRAND
			listPage = service.filterProductShopByCustomerCategoryAndBrand(valueCategory, brandList, valueIdShop,
					pageRequest);
		} else {
			// ALL
			listPage = service.filterProductShopByCustomerLocationAndCategoryAndBrand(locationList, valueCategory,
					brandList, valueIdShop, pageRequest);
		}
		List<ProductSearch> appList = listPage.getContent();
		List<ProductSearch> temp = new ArrayList<ProductSearch>();
		for (ProductSearch e : appList) {
			Shop shopRemove = e.getShop();
			shopRemove.setUser(null);
			e.setShop(shopRemove);
			temp.add(e);
		}
		listPage = new PageImpl<>(temp, pageRequest, temp.size());

		return ResponseEntity.ok(listPage);
	}

	/* GET CATEGORY SHOP */
	@GetMapping("/view/customer/shop/all/category")
	public ResponseEntity getListCategoryShop(@RequestParam("idShop") Optional<String> idShop) {
		String valueIdShop = idShop.orElse("");
		List<Product_child_category> list = child_category_service.findAllByShop(valueIdShop);
		return ResponseEntity.ok(list);
	}

	/* ALL PRODUCT VIEW SHOP BY CUSTOMER */

	@GetMapping("/view/customer/shop/all/product")
	public ResponseEntity getAllListProductByCustomer(@RequestParam("idShop") Optional<String> idShop,
			@RequestParam("page") Optional<Integer> page) {
		Page<ProductSearch> pageF = null;
		Optional<Shop> shop = shopService.findByIdOptional(idShop.get());
		if (shop.isEmpty()) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Của hàng không tồn tại", "idShop",
					null);
		} else {
			pageF = service.listAllProductShopByCustomer(shop.get().getId(), PageRequest.of(page.orElse(0), 10));
			List<ProductSearch> appList = pageF.getContent();
			List<ProductSearch> temp = new ArrayList<ProductSearch>();
			for (ProductSearch e : appList) {
				Shop shopRemove = e.getShop();
				shopRemove.setUser(null);
				e.setShop(shopRemove);
				temp.add(e);
			}
			pageF = new PageImpl<>(temp, PageRequest.of(page.orElse(0), 10), temp.size());
		}
		return ResponseEntity.ok(pageF);
	}

	@GetMapping("/view/shoplogin/category")
	public ResponseEntity<List<Product_child_category>> getallCategoryByShop() {
		List<Product_child_category> list = child_category_service
				.findAllByShop(auservice.getShopLogin(req.getHeader("Authorization")).getId());

		return ResponseEntity.ok(list);
	}

	@GetMapping("/view/get-product-related-shop/{id}")
	public ResponseEntity<?> getProductRelatedShop(@PathVariable("id") String id,
			@RequestParam("idcate") String idcate) {
		PageRequest pageRequest = PageRequest.of(0, 5);
		Page<ProductSearch> listPage = service.filterProductShopByCustomerCategory(idcate, id, pageRequest);
		return ResponseEntity.ok(listPage);
	}

	@GetMapping("/view/get-product-related-category/{id}")
	public ResponseEntity<?> getProductRelatedCategory(@PathVariable("id") String id) {

		PageRequest pageRequest = PageRequest.of(0, 5);
		Page<ProductSearch> listPage = service.filterProductShopByCustomerCategory2(id, pageRequest);
		return ResponseEntity.ok(listPage);
	}

	@GetMapping("/view/productsale/byshop/{id}")
	public ResponseEntity<?> getProductSaleByShop(@PathVariable("id") String id,
			@RequestParam("page") Optional<Integer> page, @RequestParam("keySearch") String keySearch) {
		PageRequest pageRequest = PageRequest.of(page.orElse(0), 10);
		List<ProductSearch> listP = service.getProductAllByIdShop(id, keySearch);
		List<ProductSearch> listResultList = new ArrayList<>();
		for (ProductSearch e : listP) {
			e.getProductOptions().stream().forEach(item -> {
				if (!item.getProductDiscount().isEmpty()) {
					listResultList.add(e);
				}
			});
		}
		listResultList.stream().forEach((e -> System.err.println(e)));
		Page<ProductSearch> pages = new PageImpl<>(listResultList, pageRequest, 10);
		return ResponseEntity.ok(pages);
	}

	@GetMapping("/view/5productdiscount/byshop/{id}")
	public ResponseEntity<?> get5ProductSaleByShop(@PathVariable("id") String id) {
		List<ProductSearch> listP = service.getProductAllByIdShop(id, "");
		List<ProductSearch> listResultList = new ArrayList<>();
		for (ProductSearch e : listP) {
			e.getProductOptions().stream().forEach(item -> {
				if (!item.getProductDiscount().isEmpty()) {
					listResultList.add(e);
				}
			});
		}
		if (listResultList.size() >= 5) {
			return ResponseEntity.ok(listResultList.subList(0, 5));
		}
		// return ResponseEntity.ok(listResultList.subList(0, listResultList.size()));
		return ResponseEntity.ok(listResultList);
	}

	@GetMapping("/view/5productnew/byshop/{id}")
	public ResponseEntity<?> get5ProductNewByShop(@PathVariable("id") String id,
			@RequestParam("page") Optional<Integer> page) {
		PageRequest pageRequest = PageRequest.of(page.orElse(0), 5);
		Page<ProductSearch> listPage = service.getProductNewByIdShop(id, "", pageRequest);
		return ResponseEntity.ok(listPage);
	}

	@GetMapping("/view/productnew/byshop/{id}")
	public ResponseEntity<?> getProductNewByShop(@PathVariable("id") String id,
			@RequestParam("page") Optional<Integer> page, @RequestParam("keySearch") String keySearch) {
		PageRequest pageRequest = PageRequest.of(page.orElse(0), 10);
		Page<ProductSearch> listPage = service.getProductNewByIdShop(id, keySearch, pageRequest);
		return ResponseEntity.ok(listPage);
	}

	@GetMapping("/view/productsalling/byshop/{id}")
	public ResponseEntity<?> getProductSallingByShop(@PathVariable("id") String id,
			@RequestParam("page") Optional<Integer> page, @RequestParam("keySearch") String keySearch) {
		PageRequest pageRequest = PageRequest.of(page.orElse(0), 10);
		List<ProductSearch> listP = service.getProductAllByIdShop(id, keySearch);
		List<Object> listop = order_detail_service.findAllOptionProductInOrderDetailByIdShop(id);
		List<ProductSearch> listResultList = new ArrayList<>();
		for (int i = 0; i < listop.size() - 1; i++) {
			Object[] row = (Object[]) listop.get(i);
			for (ProductSearch e : listP) {
				if (e.getIdProduct().equals(row[0])) {
					listResultList.add(e);
				}
			}
		}
		Page<ProductSearch> pages = new PageImpl<>(listResultList, pageRequest, 10);
		System.err.println(keySearch);
		return ResponseEntity.ok(pages);
	}

	@GetMapping("/view/5productsalling/byshop/{id}")
	public ResponseEntity<?> get5ProductSallingByShop(@PathVariable("id") String id,
			@RequestParam("page") Optional<Integer> page) {
		List<ProductSearch> listP = service.getProductAllByIdShop(id, "");
		List<Object> listop = order_detail_service.findAllOptionProductInOrderDetailByIdShop(id);
		List<ProductSearch> listResultList = new ArrayList<>();
		for (int i = 0; i < listop.size() - 1; i++) {
			Object[] row = (Object[]) listop.get(i);
			for (ProductSearch e : listP) {
				if (e.getIdProduct().equals(row[0])) {
					listResultList.add(e);
				}
			}
		}
		if (listResultList.size() >= 5) {
			return ResponseEntity.ok(listResultList.subList(0, 5));
		}
		return ResponseEntity.ok(listResultList.subList(0, listResultList.size()));
	}

	@GetMapping("/view/product-meta/byIdUser")

	public ResponseEntity getProductMetaByIdUser(@RequestParam("idUser") Optional<String> idUser,
			@RequestParam("idProduct") Optional<String> idProduct) {
		long millis = System.currentTimeMillis();
		Date date = new Date(millis);
		String id_user = idUser.orElse("");
		String id_product = idProduct.orElse("");

		Product_meta find = product_meta_service.findByIdUser(id_user, id_product, date);
		if (find != null) {
			find.setUser(null);
		}
		return ResponseEntity.ok(find);
	}

	@GetMapping("/view/productSelling")
	public ResponseEntity productSelling() {
		List<ProductSearch> listP = service.getListProductSearch();
		List<Object> listop = order_detail_service.findAllOptionProductInOrderDetail();
		List<ProductSearch> listResultList = new ArrayList<>();
		for (int i = 0; i < listop.size(); i++) {
			Object[] row = (Object[]) listop.get(i);
			for (ProductSearch e : listP) {
				if (e.getIdProduct().equals(row[0])) {
					Shop shop = e.getShop();
					shop.setUser(null);
					e.setShop(shop);
					listResultList.add(e);
				}
			}
		}
		return ResponseEntity.ok(listResultList);
	}

	/* ALL PRODUCT DISCOUNT VIEW SHOP BY CUSTOMER */
	@GetMapping("/view/customer/shop/all/product/discount")
	public ResponseEntity getAllListProductDiscountByCustomer() {
		Shop shop = new Shop();
		try {
			shop = auservice.getShopLogin(req.getHeader("Authorization"));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}

		long millis = System.currentTimeMillis();
		Date date = new Date(millis);
		// TODO: Sửa product discount
		List<Product_discount> pageF = product_discount_service.findByIdProduct(shop.getId(), date);
		return ResponseEntity.ok(pageF);
	}

	@GetMapping("/view/customer/product/top10/tag")
	public ResponseEntity getTop10Tag() {
		List<Product_tag> listAll = product_Tag_service.findAll();
		List<Object> listTop10 = product_tag_search_service.getTop10Tag();
		List<Product_tag> listResultList = new ArrayList<>();
		for (int i = 0; i < listTop10.size(); i++) {
			Object[] row = (Object[]) listTop10.get(i);
			for (Product_tag e : listAll) {
				if (e.getId().equals(row[0])) {
					listResultList.add(e);
				}
			}
		}
		return ResponseEntity.ok(listResultList);
	}
}
