package com.ede.edeorderservice.implement;

import org.springframework.beans.factory.annotation.Autowired;

import com.ede.edeorderservice.dao.OrderDetailDao;
import com.ede.edeorderservice.service.Order_Detail_service;

public class Order_Detail_impl implements Order_Detail_service{

	@Autowired
	OrderDetailDao dao;
	
	@Override
	public Long getCountProductOder(String id) {
		// TODO Auto-generated method stub
		return dao.getCountProductOder(id);
	}

}
