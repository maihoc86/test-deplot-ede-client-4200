package com.ede.edeorderservice.restcontroller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edeorderservice.service.Order_discount_service;

@RestController
@RequestMapping("/ede-order/admin")
@SuppressWarnings("rawtypes")
public class ReadOrderAdminRestController {
	@Autowired
	Order_discount_service service;

	/**
	 * Hàm lấy tất cả giảm giá hóa đơn
	 * 
	 * @return {listObj}
	 */
	@GetMapping("/getAll/discount/order")
	public ResponseEntity getAllDiscountOrder() {
		return ResponseEntity.ok(service.listAllStatusTrue());
	}

	/**
	 * Hàm lấy ra chi tiết hóa đơn nào sẽ được giảm giá theo điều kiện
	 * 
	 * @param id
	 * @return {obj}
	 */
	@GetMapping("/discount/order/{id}")
	public ResponseEntity getDiscountOrderById(@PathVariable("id") String id) {
		return ResponseEntity.ok(service.findById(id));
	}
}
