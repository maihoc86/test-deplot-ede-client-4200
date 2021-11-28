package com.ede.edesystemservice.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edesystemservice.service.PaymentService;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/ede-system/payment")
public class PaymentRestController {

	@Autowired
	PaymentService paymentService;

	@GetMapping("/user/getPaymentById")
	public ResponseEntity getPaymentByName(@RequestParam("id") String id) {
		return ResponseEntity.ok(paymentService.getPaymentByName(id));
	}

	@GetMapping("/user/getAllPayment")
	public ResponseEntity getAllPayment() {
		return ResponseEntity.ok(paymentService.getAllPayment());
	}

}
