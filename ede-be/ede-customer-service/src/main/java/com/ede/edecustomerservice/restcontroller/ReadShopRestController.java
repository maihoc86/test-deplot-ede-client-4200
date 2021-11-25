package com.ede.edecustomerservice.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edecustomerservice.entity.Shop;
import com.ede.edecustomerservice.service.Auth_Service;
import com.ede.edecustomerservice.service.CustomerService;
import com.ede.edecustomerservice.service.ShopService;
@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/ede-customer/shop")
public class ReadShopRestController {
	@Autowired
	Auth_Service authService;
	@Autowired
	ShopService shopService;

	@Autowired
	CustomerService customerService;

	
	@GetMapping("/info/{id}/view")
	public ResponseEntity getInfoShopbyIduser(@PathVariable("id")String id) {
		Shop shop = new Shop();
		try {
			 shop = authService.getShopLogin(id);
		} catch (Exception e) {
			return	ResponseEntity.notFound().build();
		}
		return	ResponseEntity.ok(shop);
	}
	
	@GetMapping("/viewallbyname")
	public ResponseEntity<?> getAllShopbyName(@RequestParam("name")String name) {
		List<Shop> list = shopService.findAllByName(name);
		return	ResponseEntity.ok(list);
	}
	
	// Lấy ra thông tin cửa hàng bằng ID
	
	@GetMapping("/view/info/{id}")
	public ResponseEntity<?> getShopById(@PathVariable("id") String id) {
		Shop shop = shopService.findById(id).get();
		shop.setUser(null);
		return	ResponseEntity.ok(shop);
	}
	
	
}
