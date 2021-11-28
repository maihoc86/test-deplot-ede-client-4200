package com.ede.edesystemservice.service;

import java.util.List;

import com.ede.edesystemservice.entity.payment.Payment;

public interface PaymentService {

	Payment getPaymentByName(String id);

	List<Payment> getAllPayment();

}
