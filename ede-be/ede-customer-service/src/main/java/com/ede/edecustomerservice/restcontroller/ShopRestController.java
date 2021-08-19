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

import com.ede.edecustomerservice.ResponseHandler;
import com.ede.edecustomerservice.entity.Shop;
import com.ede.edecustomerservice.entity.User;
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
		User findUser = customerService.findById("0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8"); // chỗ này điền id user đã đăng nhập vào
		shop.setUser(findUser);			
		Shop find = shopService.findById(shop.getId());
		if (find.getUser().getId().equals(findUser.getId())) {
			return ResponseEntity.status(HttpStatus.OK).body(shopService.save(shop));
			// Sửa thành công
		} else {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true,
					"Bạn không được sửa thông tin cửa hàng khác", "username", null);
		}
	}
	// Lấy thông tin của Shop
	@GetMapping("/shop/info/{id}")
	public Shop getInfoShop(@PathVariable String id) {
		return shopService.findById(id);
	}

}
