package com.ede.edeorderservice.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edeorderservice.service.Order_Detail_service;

@RestController
@RequestMapping("/ede-order")
public class ReadOrderRestController {

	@Autowired
	Order_Detail_service order_detail_service;
	
	
	@GetMapping("/view/countProductOder/{id}")
	public Long  countProductOder(@PathVariable("id") String id){
		return order_detail_service.getCountProductOder(id);
	}
}
