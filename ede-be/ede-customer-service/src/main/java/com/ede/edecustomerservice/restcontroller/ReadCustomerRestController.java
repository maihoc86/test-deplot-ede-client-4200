package com.ede.edecustomerservice.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edecustomerservice.entity.User;
import com.ede.edecustomerservice.service.CustomerService;

@RestController
@RequestMapping("/ede-customer/admin")
public class ReadCustomerRestController {

	
	@Autowired
	CustomerService service;
	
	
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
	 * @author Thanh
	 */
	@GetMapping("/admin/search/{username}")
	public List<User> search(@PathVariable("username") String username){
		System.out.println("username: "+username);
		if(username == "") {
			System.out.println("ahihi lala ");
			return this.getAccounts();
			
		}
		else {
			return service.findByUsernameContaining(username);
		}
		
	}
	@GetMapping("/findbyusername/{username}")
	public User findbyusername(@PathVariable("username") String username) {
		String u =	username.substring(1, username.length()-1);
		System.err.println("in api :" +service.findByUsername(u)+" id la " +username);
		return service.findByUsername(u);
	}
}
