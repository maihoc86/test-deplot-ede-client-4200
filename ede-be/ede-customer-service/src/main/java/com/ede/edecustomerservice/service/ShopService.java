package com.ede.edecustomerservice.service;

import com.ede.edecustomerservice.entity.Shop;

public interface ShopService {

	Shop save(Shop shop);

	Shop findById(String id);


}
