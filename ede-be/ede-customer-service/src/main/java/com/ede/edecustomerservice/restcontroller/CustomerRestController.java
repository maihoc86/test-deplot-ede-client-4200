package com.ede.edecustomerservice.restcontroller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edecustomerservice.entity.User;
import com.ede.edecustomerservice.service.CustomerService;

@CrossOrigin("*")
@RestController
public class CustomerRestController {

	@Autowired
	CustomerService service;
	@PostMapping("/ede-customer/register")
	public ResponseEntity register(@RequestBody User user) {
		UUID uuid = UUID.randomUUID();
		user.setId(uuid.toString());
		return ResponseEntity.status(HttpStatus.OK).body(this.service.saveUser(user));
	}
}
