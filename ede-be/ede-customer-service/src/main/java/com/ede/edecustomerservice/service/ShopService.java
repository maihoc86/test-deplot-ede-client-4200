package com.ede.edecustomerservice.service;

import java.util.List;
import java.util.Optional;

import com.ede.edecustomerservice.entity.Shop;
import com.ede.edecustomerservice.entity.User;

public interface ShopService {

	Shop save(Shop shop);

	Optional<Shop> findById(String id);

	Shop findByUser(User us);

	List<Shop> findAll();

	List<Shop> findAllByName(String name);


}
