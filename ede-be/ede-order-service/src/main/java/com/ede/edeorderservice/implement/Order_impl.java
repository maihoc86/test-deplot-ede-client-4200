package com.ede.edeorderservice.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ede.edeorderservice.dao.OrderDao;
import com.ede.edeorderservice.entity.Order;
import com.ede.edeorderservice.service.Order_service;


@Service
public class Order_impl implements Order_service{

	@Autowired
	OrderDao dao;
	
	/**
	 * @author thái học
	 *
	 * 
	 */
	@Override
	public List<Order> searchOrderStatus(String keyword, String status) {
		return dao.searchOrderStatus(keyword,status);
	}

	/**
	 * @author thái học
	 *
	 * 
	 */
	@Override
	public List<Order> searchOrderAll(String keyword) {
		return dao.searchOrderAll(keyword);
	}

}
