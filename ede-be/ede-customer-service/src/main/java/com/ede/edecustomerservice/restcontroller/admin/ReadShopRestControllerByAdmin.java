package com.ede.edecustomerservice.restcontroller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edecustomerservice.entity.Shop;
import com.ede.edecustomerservice.service.CustomerService;
import com.ede.edecustomerservice.service.ShopService;

@RestController
@RequestMapping("/ede-customer/shop/admin")
public class ReadShopRestControllerByAdmin {
	@Autowired
	ShopService shopService;

	@Autowired
	CustomerService customerService;

	@GetMapping("/getNewShops")
	public List<Shop> getNewShop() {
		return shopService.getNewShop();
	}

	@GetMapping("/viewall")
	public ResponseEntity<?> getAllShop() {
		List<Shop> list = shopService.findAll();
		return ResponseEntity.ok(list);
	}

}
