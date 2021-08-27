package com.ede.edecustomerservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ede.edecustomerservice.entity.Shop;
import com.ede.edecustomerservice.entity.User;

public interface ShopDao extends JpaRepository<Shop, String>{

	Shop findByUser(User us);



}
