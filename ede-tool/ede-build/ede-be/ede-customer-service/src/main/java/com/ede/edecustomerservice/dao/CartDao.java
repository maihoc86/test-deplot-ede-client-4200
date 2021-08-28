package com.ede.edecustomerservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ede.edecustomerservice.entity.Cart;

public interface CartDao extends JpaRepository<Cart, String> {

}
