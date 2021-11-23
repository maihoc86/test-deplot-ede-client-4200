package com.ede.edeorderservice.implement;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ede.edeorderservice.dao.OrderDetailDao;
import com.ede.edeorderservice.entity.Orderdetail;
import com.ede.edeorderservice.service.Order_Detail_service;

@Service
public class Order_Detail_impl implements Order_Detail_service {

	@Autowired
	OrderDetailDao dao;

	@Override
	public Long getCountProductOder(String id) {
		return dao.getCountProductOder(id);
	}

	/**
	 * @author thái học
	 *
	 * 
	 */
	@Override
	public List<Orderdetail> searchOrderDetailAll(String keyword) {
		return dao.searchOrderDetailAll(keyword);
	}

	/**
	 * @author thái học
	 *
	 * 
	 */
	@Override
	public Page<Orderdetail> listAll(String idOrder, String keyword, Pageable page) {
		return dao.listAll(idOrder, keyword, page);
	}

	@Override

	public List<Orderdetail> getProductSellCurrentMonth(Date date) {
		return dao.getProductSellCurrentMonth(date);
	}

	public List<Orderdetail> findAllByUser(String idUser) {
		return dao.findAllByUser(idUser);
	}



	@Override
	public List<Orderdetail> getProductSell() {
		return dao.findAll();
	}

}
