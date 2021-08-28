package com.ede.edecustomerservice.service;

import java.util.Optional;

import com.ede.edecustomerservice.entity.Shop;

public interface ShopService {

	Shop save(Shop shop);

	Optional<Shop> findById(String id);


}
