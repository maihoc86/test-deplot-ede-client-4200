package com.ede.edecustomerservice.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ede.edecustomerservice.dao.ShopDao;
import com.ede.edecustomerservice.entity.Shop;
import com.ede.edecustomerservice.service.ShopService;

@Service
@Transactional
public class ShopImpl implements ShopService {

	@Autowired
	ShopDao dao;

	@Override
	public Shop save(Shop shop) {
		return dao.save(shop);
	}

	@Override
	public Shop findById(String id) {
		return dao.findById(id).get();
	}

}
