package com.ede.edecustomerservice.restcontroller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edecustomerservice.entity.HistoryViewPage;
import com.ede.edecustomerservice.entity.User;
import com.ede.edecustomerservice.service.CustomerService;
import com.ede.edecustomerservice.service.History_View_Page_Service;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/ede-customer/admin")
public class ReadCustomerRestControllerByAdmin {
	@Autowired
	CustomerService service;

	@Autowired
	History_View_Page_Service history_View_Page_Service;
	/**
	 * Load data on the table
	 * 
	 * @author thanh
	 * @see
	 */
	@GetMapping("/users")
	public List<User> getAccounts() {
		return service.findAll();
	}

	@GetMapping("/getNewUsers")
	public List<User> getNewUsers() {
		return service.getNewUsers();
	}

	/**
	 * Create search account admin
	 * 
	 * @author Thanh
	 */
	@GetMapping("/search/{username}")
	public List<User> search(@PathVariable("username") String username) {
		System.out.println("username: " + username);
		if (username == "") {
			return this.getAccounts();
		} else {
			return service.findByUsernameContaining(username);
		}

	}
	
	@GetMapping("/getViewPage")
	public List<HistoryViewPage> getHistoryViewPages(){
		return history_View_Page_Service.listAll();
	}
	
	@GetMapping("/getViewPageNew")
	public List<HistoryViewPage> getHistoryViewPagesNew(){
		return history_View_Page_Service.getHistoryViewPagesNew();
	}
}
