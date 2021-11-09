package com.ede.edecustomerservice.restcontroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edecustomerservice.entity.User;
import com.ede.edecustomerservice.service.Auth_Service;
import com.ede.edecustomerservice.service.CustomerService;
import com.ede.edecustomerservice.service.UserAddress_Service;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/ede-customer")
public class ReadCustomerRestController {

	@Autowired
	CustomerService service;

	@Autowired
	Auth_Service auth_service;

	@Autowired
	UserAddress_Service address_Service;

	/**
	 * Load data on the table
	 * 
	 * @author thanh
	 * @see
	 */
	@GetMapping("/admin/users")
	public List<User> getAccounts() {
		return service.findAll();
	}

	/**
	 * Create search account admin
	 * 
	 * @author Thanh
	 */
	@GetMapping("/admin/search/{username}")
	public List<User> search(@PathVariable("username") String username) {
		System.out.println("username: " + username);
		if (username == "") {
			return this.getAccounts();

		} else {
			return service.findByUsernameContaining(username);
		}

	}

	@GetMapping("/findbyusername/{username}")
	public User findbyusername(@PathVariable("username") String username) {
		String u = username.substring(1, username.length() - 1);
		System.out.println(u);
		return service.findByUsername(u);
	}

	@GetMapping("/user/address/getAll")
	public ResponseEntity getAllAddressUser(HttpServletRequest req) {
		User userLogin = new User();
		try {
			userLogin = auth_service.getUserLogin(req.getHeader("Authorization"));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(address_Service.getAllAddressByUser(userLogin.getId()));

	}

}
