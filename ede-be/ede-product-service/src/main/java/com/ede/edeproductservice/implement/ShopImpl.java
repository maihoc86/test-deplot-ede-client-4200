package com.ede.edeproductservice.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ede.edeproductservice.dao.ShopDao;
import com.ede.edeproductservice.entity.Shop;
import com.ede.edeproductservice.service.ShopService;

@Service
public class ShopImpl implements ShopService {

	@Autowired
	ShopDao dao;

	@Override
	public Shop findById(String string) {
		return dao.findById(string).get();
	}

}
