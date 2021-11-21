package com.ede.edecustomerservice.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ede.edecustomerservice.dao.ShopDao;
import com.ede.edecustomerservice.entity.Shop;
import com.ede.edecustomerservice.entity.User;
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
	public Optional<Shop> findById(String id) {
		return dao.findById(id);
	}

	@Override
	public Shop findByUser(User us) {
		return dao.findByUser(us);
	}

	@Override
	public List<Shop> findAll() {
		return dao.findAll();
	}

	@Override
	public List<Shop> findAllByName(String name) {
		return dao.findAllByName("%"+name+"%");
	}

	@Override
	public List<Shop> getNewShop() {
		return dao.getNewShop();
	}

}
