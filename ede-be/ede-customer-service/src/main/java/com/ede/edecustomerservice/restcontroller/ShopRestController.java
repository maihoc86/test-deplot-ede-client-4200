package com.ede.edecustomerservice.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edecustomerservice.entity.Shop;
import com.ede.edecustomerservice.service.CustomerService;
import com.ede.edecustomerservice.service.ShopService;

@CrossOrigin("*")
@RestController
@RequestMapping("/ede-customer")
public class ShopRestController {

	@Autowired
	ShopService shopService;

	@Autowired
	CustomerService customerService;
	// Chỉnh sửa thông tin của hàng by Thái Học

	@SuppressWarnings("rawtypes")
	@PutMapping("/shop/info/update/{id}")
	public ResponseEntity updateInfoShop(@RequestBody Shop shop) {
		System.out.println(shop);
		return ResponseEntity.status(HttpStatus.OK).body(shopService.save(shop));
	}

	@GetMapping("/shop/info/{id}")
	public Shop getInfoShop(@PathVariable String id) {
		Shop shope = shopService.findById(id);
		System.out.println(shope.getUser().getUsername());
		return shopService.findById(id);
	}
	// End Thái Học **** Đang test chưa xong nga

}
