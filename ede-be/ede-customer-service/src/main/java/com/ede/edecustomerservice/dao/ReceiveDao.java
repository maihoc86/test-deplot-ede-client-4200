package com.ede.edecustomerservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ede.edecustomerservice.entity.Product_option;
import com.ede.edecustomerservice.entity.Receive_news;

public interface ReceiveDao extends JpaRepository<Product_option, String> {


	@Query("Select c from Receive_news c where c.email =:email")
	Receive_news findEmail(String email);
}
