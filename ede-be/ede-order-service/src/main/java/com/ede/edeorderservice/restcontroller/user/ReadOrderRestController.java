package com.ede.edeorderservice.restcontroller.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edeorderservice.service.Order_Detail_service;
import com.ede.edeorderservice.service.Order_service;

@RestController
@RequestMapping("/ede-order")
@SuppressWarnings("rawtypes")
public class ReadOrderRestController {

	@Autowired
	Order_Detail_service order_detail_service;

	@Autowired
	Order_service order_service;

	@GetMapping("/view/countProductOder/{id}")
	public Long countProductOder(@PathVariable("id") String id) {
		return order_detail_service.getCountProductOder(id);
	}

	@GetMapping("/view/order/shop/search")
	public ResponseEntity listOrderSearch(@RequestParam("keyword") Optional<String> keyword,
			@RequestParam("status") Optional<String> status) {
		if (status.isPresent()) {
			return ResponseEntity.ok(order_service.searchOrderStatus(keyword.get(), status.get()));
		} else {
			return ResponseEntity.ok(order_service.searchOrderAll(keyword.get()));
		}
	}

	@GetMapping("/view/order_detail/shop/search")
	public ResponseEntity listOrderDetailSearch(@RequestParam("keyword") Optional<String> keyword) {
		return ResponseEntity.ok(order_detail_service.searchOrderDetailAll(keyword.get()));

	}
	@GetMapping("/view/orderDetail/getAll")
	public ResponseEntity getAll() {
		return ResponseEntity.ok(order_detail_service.listAll());

	}
}
