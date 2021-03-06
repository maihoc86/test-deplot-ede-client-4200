package com.ede.edecustomerservice.restcontroller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edecustomerservice.entity.User;
import com.ede.edecustomerservice.service.CustomerService;

@RestController
public class CustomerManagerRestController {
	
	@Autowired
	private CustomerService customerService;
	

	@Autowired
	CustomerService service;

	
	/**
	 * 
	 * @author vinh
	 * @param userBody
	 * @return
	 */
	@PostMapping("/ede-customer/admin/update-customer")
	ResponseEntity<User> updateUser(@RequestBody User userBody) {
		if (null == userBody || null == userBody.getUsername()) {
			return ResponseEntity.badRequest().header("message", "User is null").build();
		}
		if (!this.customerService.existsUsername(userBody.getUsername())) {
			return ResponseEntity.badRequest().header("message", "User dont Exists").build();
		}

		User userOrigin = this.customerService.findByUsername(userBody.getUsername());
		
		userOrigin.setFirst_name(userBody.getFirst_name());
		userOrigin.setLast_name(userBody.getLast_name());
		userOrigin.setUsername(userBody.getUsername());
		userOrigin.setPhone(userBody.getPhone());
		userOrigin.setGender(userBody.getGender());
		userOrigin.setEmail(userBody.getEmail());
		userOrigin.setAddress(userBody.getAddress());
		userOrigin.setPassword(userBody.getPassword());
		
		this.customerService.updateUserById(userOrigin);
		
		return ResponseEntity.ok(userOrigin);
	}
	

}
