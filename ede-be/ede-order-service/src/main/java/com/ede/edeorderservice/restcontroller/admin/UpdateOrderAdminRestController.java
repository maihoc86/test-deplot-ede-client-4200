package com.ede.edeorderservice.restcontroller.admin;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edeorderservice.entity.Order_Discount;
import com.ede.edeorderservice.service.Order_discount_service;
@RestController
@RequestMapping("/ede-order/admin")
@SuppressWarnings("rawtypes")
public class UpdateOrderAdminRestController {
	@Autowired
	Order_discount_service service;

	public String generateUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * Hàm thêm giảm giá cho tổng giá trị hóa đơn (toàn hệ thống)
	 */
	@PutMapping("/update/discount/order")
	public ResponseEntity createDiscountOrder(@RequestBody Order_Discount order_Discount) {
		return ResponseEntity.ok(service.save(order_Discount));
	}
}
