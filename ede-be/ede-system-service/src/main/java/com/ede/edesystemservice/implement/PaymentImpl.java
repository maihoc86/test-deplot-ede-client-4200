package com.ede.edesystemservice.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ede.edesystemservice.dao.PaymentDao;
import com.ede.edesystemservice.entity.payment.Payment;
import com.ede.edesystemservice.service.PaymentService;

@Service
public class PaymentImpl implements PaymentService {
	@Autowired
	PaymentDao dao;

	@Override
	public Payment getPaymentByName(String id) {
		return dao.findById(id).get();
	}

	@Override
	public List<Payment> getAllPayment() {
		return dao.findAll();
	}


}
