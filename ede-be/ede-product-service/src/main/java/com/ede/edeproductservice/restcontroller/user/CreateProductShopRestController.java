package com.ede.edeproductservice.restcontroller.user;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ede.edeproductservice.ResponseHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.entity.Product_discount;
import com.ede.edeproductservice.entity.Product_option;
import com.ede.edeproductservice.entity.Product_option_image;
import com.ede.edeproductservice.entity.Product_tag;
import com.ede.edeproductservice.entity.Shop;
import com.ede.edeproductservice.entity.User;
import com.ede.edeproductservice.service.ProductService;
import com.ede.edeproductservice.service.Product_Tag_service;
import com.ede.edeproductservice.service.Product_brand_service;
import com.ede.edeproductservice.service.Product_child_category_service;
import com.ede.edeproductservice.service.Product_discount_service;
import com.ede.edeproductservice.service.Product_option_image_service;
import com.ede.edeproductservice.service.Product_option_service;
import com.ede.edeproductservice.service.ShopService;

@RestController
@RequestMapping("/ede-product")
public class CreateProductShopRestController {

	@Autowired
	ProductService service;

	@Autowired
	Product_brand_service brandService;

	@Autowired
	Product_child_category_service category_service;

	@Autowired
	Product_option_service product_option_service;

	@Autowired
	Product_option_image_service product_option_image_service;

	@Autowired
	Product_Tag_service product_Tag_service;

	@Autowired
	Product_discount_service product_discount_service;
	
	@Autowired
	ShopService shopService;

	@SuppressWarnings("rawtypes")
	@PostMapping("/create/product-shop")
	public ResponseEntity addProductAndSell(@RequestBody Product product, HttpServletRequest req) {

		System.err.println(req.getHeader("Content-Type"));
		User us = new User();
		try {
			us = checkLogin(req.getHeader("Authorization"));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		
		System.out.println("US: " + us);

		UUID uuid = UUID.randomUUID();
		product.setId(uuid.toString());
		product.setEnable(false);
		/************************/

		Shop sh = shopService.findByUser(us);
		System.err.println("shop : " + sh);
		product.setShop(sh);

		return ResponseEntity.status(HttpStatus.OK).body(service.save(product));
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/create/product-shop/options/{id}")
	public ResponseEntity addProductOptions(@RequestBody Product_option product_option,
			@PathVariable("id") String id_product) {
		System.err.println("option: " + product_option);
		UUID uuid = UUID.randomUUID();
		product_option.setId(uuid.toString());
		product_option.setProduct(service.findById(id_product));
		return ResponseEntity.status(HttpStatus.OK).body(product_option_service.save(product_option));
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/create/product-shop/options/images")
	public ResponseEntity addProductOptionImage(@RequestBody Product_option_image product_option) {
		String[] words = product_option.getImage().split(",");
		for (int i = 0; i < words.length; i++) {
			UUID uuid = UUID.randomUUID();
			Optional<Product_option_image> findImage = product_option_image_service.findById(uuid.toString());
			if (findImage.isPresent() && findImage != null) {
				UUID uuid2 = UUID.randomUUID();
				product_option.setId(uuid2.toString());
				String[] fileCat = words[i].split("\\.");
				product_option.setImage(uuid.toString() + "." + fileCat[1]);
				product_option_image_service.save(product_option);
			} else {
				product_option.setId(uuid.toString());
				String[] fileCat = words[i].split("\\.");
				product_option.setImage(uuid.toString() + "." + fileCat[1]);
				product_option_image_service.save(product_option);
			}
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "Thêm hình ảnh thành công", "", null);
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping("/create/product-shop/discount")
	public ResponseEntity addProductDiscount(@RequestBody Product_discount product_discount) {
		return ResponseEntity.status(HttpStatus.OK).body(product_discount_service.save(product_discount));
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/create/product-shop/tag")
	public ResponseEntity addProductTag(@RequestBody Product_tag product_tag) {
		String[] words = product_tag.getTag().split(",");
		for (int i = 0; i < words.length; i++) {
			UUID uuid = UUID.randomUUID();
			Optional<Product_tag> findTag = product_Tag_service.findById(uuid.toString());
			if (findTag.isPresent() && findTag != null) {
				UUID uuid2 = UUID.randomUUID();
				product_tag.setId(uuid2.toString());
				product_tag.setTag(words[i]);
				product_Tag_service.save(product_tag);
			} else {
				product_tag.setId(uuid.toString());
				product_tag.setTag(words[i]);
				product_Tag_service.save(product_tag);
			}
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "Thêm tag thành công", "", null);
	}

	@SuppressWarnings("rawtypes")
	@PutMapping("/enable/product-shop/{id}")
	public ResponseEntity enableProductAndSell(@PathVariable("id") String id) {
		Product product = service.findById(id);
		product.setEnable(true);
		return ResponseEntity.status(HttpStatus.OK).body(service.save(product));
	}

	
	
	public User checkLogin(String headers) {
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		header.add("Authorization", "Bearer " + headers);

		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/ede-oauth-service/api/auth/check/login";
		HttpEntity<Object> entity = new HttpEntity<Object>(null, header);
		ResponseEntity<JsonNode> respone = restTemplate.exchange(url, HttpMethod.POST, entity, JsonNode.class);
		JsonNode jsonNode = respone.getBody();

		System.err.println(jsonNode.get("id"));

		String url2 = "http://localhost:8080/ede-customer/findbyusername/" + jsonNode.get("id");
		ResponseEntity<User> user = restTemplate.exchange(url2, HttpMethod.GET, entity, User.class);
		return user.getBody();
	}
}
