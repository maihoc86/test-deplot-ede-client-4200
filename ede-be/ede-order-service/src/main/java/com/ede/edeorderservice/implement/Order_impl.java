package com.ede.edeorderservice.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ede.edeorderservice.dao.OrderDao;
import com.ede.edeorderservice.entity.Order;
import com.ede.edeorderservice.service.Order_service;

@Service
public class Order_impl implements Order_service {

	@Autowired
	OrderDao dao;

	/**
	 * @author thái học
	 *
	 * 
	 */
	@Override
	public List<Order> searchOrderStatus(String keyword, String status) {
		return dao.searchOrderStatus(keyword, status);
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

	/**
	 * @author thái học
	 *
	 * 
	 */
	@Override
	public List<Order> findAll() {
		return dao.findAll();
	}

	/**
	 * @author thái học
	 *
	 * 
	 */
	@Override
	public Page<Order> findAllOrderByShop(String keyword,String id,Pageable page) {
		return dao.findAllOrderByShop(keyword,id,page);
	}

	/**
	 * @author thái học
	 *
	 * 
	 */
	@Override
	public Page<Order> findAllOrderShopByStatus(String keyword ,String shop, String status, Pageable page) {
		return dao.findAllOrderShopByStatus(keyword, shop, status, page);
	}

	@Override
	public Order findDiscount(String id) {
		return dao.findDiscount(id);
	}

	@Override
	public List<Order> findAllByUser(String idUser) {
		return dao.findAllByUser(idUser);
	}

	@Override
	public List<Order> findOrderDiscountUser(String id, String idDiscount) {
		return dao.findOrderDiscountUser(id, idDiscount);
	}

}
