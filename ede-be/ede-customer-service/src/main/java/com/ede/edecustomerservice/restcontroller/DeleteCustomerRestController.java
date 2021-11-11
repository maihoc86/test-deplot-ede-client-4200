package com.ede.edecustomerservice.restcontroller;

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
import com.ede.edecustomerservice.entity.User;
import com.ede.edecustomerservice.entity.UserAddress;
import com.ede.edecustomerservice.service.Auth_Service;
import com.ede.edecustomerservice.service.CustomerService;
import com.ede.edecustomerservice.service.UserAddress_Service;

@SuppressWarnings("rawtypes")
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

	@DeleteMapping("/user/delete-address/{id}")
	public ResponseEntity deleteAddress(@PathVariable("id") String id, HttpServletRequest req) {

		User userLogin = new User();
		try {
			userLogin = auth_service.getUserLogin(req.getHeader("Authorization"));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}

		UserAddress findAddress = address_Service.findById(id);

		if (findAddress != null) {

			if (userLogin.getId().equals(findAddress.getUser().getId())) {
				UserAddress findById = address_Service.getAddressByUser(userLogin.getId(), findAddress.getAddress());
				if (findById == null) {
					return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true,
							"Địa chỉ này không tồn tại trên tài khoản của bạn", "address", null);
				} else {
					System.err.println("Vào đây");
					address_Service.deleteById(id);
					return ResponseHandler.generateResponse(HttpStatus.OK, false, "Xóa thành công", "address", null);
				}
			} else {
				return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true,
						"Bạn không được xóa địa chỉ người khác", "address", null);
			}

		} else {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Địa chỉ không tồn tại", "address",
					null);
		}

	}
}
