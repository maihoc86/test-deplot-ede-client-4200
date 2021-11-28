package com.ede.edesystemservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ede.edesystemservice.entity.payment.Payment;

public interface PaymentDao extends JpaRepository<Payment, String> {

	@Query("Select p from Payment p where p.name =:name")
	Payment getPaymentByName(String name);


}
