package com.ede.edecustomerservice.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edecustomerservice.entity.User;
import com.ede.edecustomerservice.service.CustomerService;
@RestController
@RequestMapping("/ede-customer")
public class DeleteCustomerRestController {

	@Autowired
	CustomerService service;
	
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
}
