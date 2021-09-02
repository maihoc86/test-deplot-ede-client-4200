package com.ede.edecustomerservice.restcontroller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edecustomerservice.ResponseHandler;
import com.ede.edecustomerservice.entity.Shop;
import com.ede.edecustomerservice.service.Auth_Service;
import com.ede.edecustomerservice.service.CustomerService;
import com.ede.edecustomerservice.service.ShopService;

@RestController
@RequestMapping("/ede-customer/shop")
public class UpdateShopRestController {
	@Autowired
	ShopService shopService;
	@Autowired
	Auth_Service auth_service;

	@Autowired
	CustomerService customerService;
	// Chỉnh sửa thông tin của hàng by Thái Học

	@SuppressWarnings("rawtypes")
	@PutMapping("/info/update/")
	public ResponseEntity updateInfoShop(@RequestBody Shop shop, HttpServletRequest req) {
		Shop shopLogin = new Shop();
		try {
			shopLogin = auth_service.getShopLogin(req.getHeader("Authorization"));
		} catch (Exception e) {
		return ResponseEntity.notFound().build();
		}
		
		if (shop.getUser().getId().equals(shopLogin.getUser().getId())) {
			if(shopLogin.getImage() == null || shopLogin.getImage().equals("")) {
				shopLogin.setImage(shop.getImage());
			}
			return ResponseEntity.status(HttpStatus.OK).body(shopService.save(shop));
		} else {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true,
					"Bạn không được sửa thông tin cửa hàng khác", "username", null);
		}
	}
}
