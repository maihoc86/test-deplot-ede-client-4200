package com.ede.edecustomerservice.restcontroller;

import java.net.http.HttpRequest;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edecustomerservice.ResponseHandler;
import com.ede.edecustomerservice.entity.Shop;
import com.ede.edecustomerservice.entity.User;
import com.ede.edecustomerservice.entity.UserAddress;
import com.ede.edecustomerservice.service.Auth_Service;
import com.ede.edecustomerservice.service.CustomerService;
import com.ede.edecustomerservice.service.UserAddress_Service;

@RestController
@RequestMapping("/ede-customer")
public class DeleteCustomerRestController {

	@Autowired
	CustomerService service;

	@Autowired
	Auth_Service auth_service;

	@Autowired
	UserAddress_Service address_Service;

	@DeleteMapping("/admin/delete/users/{username}")
	public ResponseEntity<User> deleteUserByUsername(@PathVariable("username") String username) {
		System.err.println("Detele username :" + username);
		try {
			return ResponseEntity.ok(service.deleteByUsername(username));
		} catch (Exception e) {
			System.err.println(e);
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/delete-address")
	public ResponseEntity deleteAddress(@RequestBody UserAddress address, HttpServletRequest req) {

		User userLogin = new User();
		try {
			userLogin = auth_service.getUserLogin(req.getHeader("Authorization"));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}

		if (userLogin.getId().equals(address.getUser().getId())) {

			User findUser = service.findById(address.getUser().getId()).get();

			if (findUser == null) {
				return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "User không tồn tại", "user",
						null);
			}

			UserAddress findById = address_Service.findByUserId(address.getUser().getId());
			if (findById == null) {
				return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true,
						"Địa chỉ này không tồn tại trên tài khoản của bạn", "address", null);
			}
			address_Service.deleteById(address.getId());
			return ResponseEntity.status(HttpStatus.OK).body("Xóa thành công");
		} else {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true,
					"Bạn không được xóa địa chỉ người kh", "address", null);
		}

	}
}
