package com.ede.edeorderservice.restcontroller.admin;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edeorderservice.ResponseHandler;
import com.ede.edeorderservice.entity.Order_Discount;
import com.ede.edeorderservice.service.Order_discount_service;

@RestController
@RequestMapping("/ede-order/admin")
@SuppressWarnings("rawtypes")
public class CreateOrderAdminRestController {

	@Autowired
	Order_discount_service service;

	public String generateUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * Hàm thêm giảm giá cho tổng giá trị hóa đơn (toàn hệ thống)
	 */
	@PostMapping("/create/discount/order")
	public ResponseEntity createDiscountOrder(@RequestBody Order_Discount order_Discount) {
		order_Discount.setId(generateUUID());
		List<Order_Discount> findOrderDiscountDate = service.findOrderDiscountDate(order_Discount.getTodate(),order_Discount.getEnddate());	
		if(findOrderDiscountDate != null) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Trong thời gian này, đã có giảm giá trên hệ thống", "", null);
		}
		return ResponseEntity.ok(service.save(order_Discount));
	}

}
