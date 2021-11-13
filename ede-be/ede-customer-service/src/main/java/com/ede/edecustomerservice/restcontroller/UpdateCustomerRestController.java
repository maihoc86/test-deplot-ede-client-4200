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

	/**
	 * Hàm cập nhật địa chỉ
	 * 
	 * @param address
	 * @param req
	 * @return ServerResponse
	 */
	@PutMapping("/user/update-address")
	public ResponseEntity updateAddress(@RequestBody UserAddress address, HttpServletRequest req) {

		User userLogin = new User();
		try {
			userLogin = auth_service.getUserLogin(req.getHeader("Authorization"));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}

		if (userLogin.getId().equals(address.getUser().getId())) {
			return ResponseEntity.ok(address_Service.saveAddress(address));
		} else {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true,
					"Bạn không được sửa địa chỉ người khác", "address", null);
		}
	}

	/**
	 * @author thaihoc Hàm chỉnh sửa địa chỉ phụ thành địa chỉ chính
	 * @param address
	 * @param req
	 * @return ServerResponse
	 */
	@PutMapping("/user/update-main-address")
	public ResponseEntity updateMainAddress(@RequestBody UserAddress address, HttpServletRequest req) {
		User userLogin = new User();
		try {
			userLogin = auth_service.getUserLogin(req.getHeader("Authorization"));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}

		if (userLogin.getId().equals(address.getUser().getId())) {

			UserAddress newAddress = new UserAddress();
			newAddress.setId(address.getId());
			newAddress.setUser(address.getUser());
			newAddress.setFirst_name(userLogin.getFirst_name());
			newAddress.setLast_name(userLogin.getLast_name());
			newAddress.setPhone(userLogin.getPhone());
			newAddress.setAddress(userLogin.getAddress());

			userLogin.setPhone(address.getPhone());
			userLogin.setFirst_name(address.getFirst_name());
			userLogin.setLast_name(address.getLast_name());
			userLogin.setAddress(address.getAddress());

			User updateAddressMainUser = service.saveUser(userLogin);

			if (updateAddressMainUser != null) {
				UserAddress updateAddressSub = address_Service.saveAddress(newAddress);
				return ResponseEntity.ok(updateAddressSub);
			} else {
				return ResponseHandler.generateResponse(HttpStatus.METHOD_NOT_ALLOWED, true,
						"Không thể cập nhật địa chỉ", "address", null);
			}

		} else {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true,
					"Bạn không được sửa địa chỉ người khác", "address", null);
		}
	}
}
