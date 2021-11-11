package com.ede.edeorderservice.restcontroller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edeorderservice.ResponseHandler;
import com.ede.edeorderservice.entity.Order_Discount;
import com.ede.edeorderservice.service.Order_discount_service;

@RestController
@RequestMapping("/ede-order/admin")
@SuppressWarnings("rawtypes")
public class DeleteOrderAdminRestController {
	@Autowired
	Order_discount_service service;

	/**
	 * Hàm xóa giảm giá hóa đơn
	 * 
	 */
	@DeleteMapping("/delete/discount/order/{id}")
	public ResponseEntity deleteById(@PathVariable("id") String id) {
		Order_Discount findById = service.findById(id);
		if (findById == null) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Sản phẩm không tồn tại", "Id", null);
		} else if (findById.isStatus() == false) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Sản phẩm này đã bị xóa", "Id", null);
		} else {
			return ResponseEntity.ok(service.deleteById(id));
		}
	}

}
