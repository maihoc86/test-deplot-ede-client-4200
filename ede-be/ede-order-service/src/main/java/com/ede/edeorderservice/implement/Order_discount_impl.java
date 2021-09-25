package com.ede.edeorderservice.implement;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ede.edeorderservice.dao.OrderDiscountDao;
import com.ede.edeorderservice.entity.Order_Discount;
import com.ede.edeorderservice.service.Order_discount_service;

@Service
public class Order_discount_impl implements Order_discount_service {

	@Autowired
	OrderDiscountDao dao;

	@Override
	public Order_Discount save(Order_Discount order_Discount) {
		return dao.save(order_Discount);
	}

	@Override
	public List<Order_Discount> listAll() {
		return dao.findAll();
	}

	@Override
	public Order_Discount findById(String id) {
		return dao.findById(id).get();
	}

	@Override
	public Order_Discount deleteById(String id) {
		Order_Discount od = dao.findById(id).get();
		od.setStatus(false);
		dao.save(od);
		return od;
	}

	@Override
	public Order_Discount findOrderDiscountDate(Date todate) {
		return dao.findOrderDiscountDate(todate);
	}

	@Override
	public Page<Order_Discount> listAllStatusTrue(PageRequest of) {
		return dao.listAllStatusTrue(of);
	}

}
