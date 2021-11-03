package com.ede.edecustomerservice.restcontroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edecustomerservice.ResponseHandler;
import com.ede.edecustomerservice.entity.User;
import com.ede.edecustomerservice.entity.UserAddress;
import com.ede.edecustomerservice.service.Auth_Service;
import com.ede.edecustomerservice.service.CustomerService;
import com.ede.edecustomerservice.service.UserAddress_Service;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/ede-customer")
public class UpdateCustomerRestController {

	@Autowired
	CustomerService service;

	@Autowired
	Auth_Service auth_service;

	@Autowired
	UserAddress_Service address_Service;

	@PutMapping("/update-address")
	public ResponseEntity updateAddress(@RequestBody UserAddress address, HttpServletRequest req) {

		User userLogin = new User();
		try {
			userLogin = auth_service.getUserLogin(req.getHeader("Authorization"));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}

		if (userLogin.getId().equals(address.getUser().getId())) {

			List<UserAddress> findByUserId = address_Service.findByUserId(address.getUser().getId(),
					address.getAddress()); // Nếu có thay đổi địa chỉ ms cho update
			if (findByUserId != null) {
				return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true,
						"Địa chỉ này đã tồn tại trên tài khoản của bạn", "address", null);
			}
			return ResponseEntity.ok(address_Service.saveAddress(address));
		} else {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true,
					"Bạn không được sửa địa chỉ người khác", "address", null);
		}
	}
}
