package com.ede.edeorderservice.restcontroller.admin;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edeorderservice.ResponseHandler;
import com.ede.edeorderservice.entity.Order;
import com.ede.edeorderservice.entity.Order_Discount;
import com.ede.edeorderservice.service.Order_discount_service;
import com.ede.edeorderservice.service.Order_service;

@RestController
@RequestMapping("/ede-order/admin")
@SuppressWarnings("rawtypes")
public class UpdateOrderAdminRestController {
	@Autowired
	Order_discount_service service;

	@Autowired
	Order_service order_service;

	public String generateUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * Hàm thêm giảm giá cho tổng giá trị hóa đơn (toàn hệ thống)
	 */
	@PutMapping("/update/discount/order")
	public ResponseEntity createDiscountOrder(@RequestBody Order_Discount order_Discount) {

		Order orderFind = order_service.findDiscount(order_Discount.getId());
		if (orderFind != null) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true,
					"Mã giảm giá này đã được khách hàng áp dụng, không được cập nhật thông tin !", "id", null);
		} else {
			return ResponseEntity.ok(service.save(order_Discount));
		}
	}
}
