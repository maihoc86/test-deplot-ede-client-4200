package com.ede.edecustomerservice.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edecustomerservice.entity.Shop;
import com.ede.edecustomerservice.service.CustomerService;
import com.ede.edecustomerservice.service.ShopService;

@RestController
@RequestMapping("/ede-customer/shop")
public class ReadShopRestController {

	@Autowired
	ShopService shopService;

	@Autowired
	CustomerService customerService;

	// Lấy thông tin của Shop
	@GetMapping("/info/view/{id}")
	public Shop getInfoShop(@PathVariable String id) {
		return shopService.findById(id).get();
	}
}
