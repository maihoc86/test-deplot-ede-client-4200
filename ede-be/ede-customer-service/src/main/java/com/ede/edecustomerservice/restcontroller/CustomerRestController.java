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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CustomerRestController {

	@Autowired
	CustomerService service;

	@RequestMapping("/ede-customer/index")
	public String getView() {
		return "Hello World";
	}

	@PostMapping("/ede-customer/register")
	public ResponseEntity<User> register(@RequestBody User user) {
		System.out.println(user);
		UUID uuid = UUID.randomUUID();
		System.out.println(uuid);
		user.setId(uuid.toString());
		System.out.println(user);
		System.out.println("Vào đây r");
		return ResponseEntity.status(HttpStatus.OK).body(this.service.saveUser(user));
	}
}
