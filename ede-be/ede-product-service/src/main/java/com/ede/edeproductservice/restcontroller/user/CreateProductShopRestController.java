package com.ede.edeproductservice.restcontroller.user;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.entity.Product_option;
import com.ede.edeproductservice.entity.Shop;
import com.ede.edeproductservice.entity.User;
import com.ede.edeproductservice.service.ProductService;
import com.ede.edeproductservice.service.Product_brand_service;
import com.ede.edeproductservice.service.Product_child_category_service;
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
	@PostMapping("/create/product-shop/options")
	public ResponseEntity addProductOptions(@RequestBody Product_option product_option) {
		return ResponseEntity.status(HttpStatus.OK).body(product_option_service.save(product_option));
	}

	@SuppressWarnings("rawtypes")
	@PutMapping("/create/product-shop/{id}")
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
