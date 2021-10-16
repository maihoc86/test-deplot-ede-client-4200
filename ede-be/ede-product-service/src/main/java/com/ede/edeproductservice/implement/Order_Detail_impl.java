package com.ede.edeproductservice.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ede.edeproductservice.dao.OrderDetailDao;
import com.ede.edeproductservice.entity.Product_option;
import com.ede.edeproductservice.service.Order_Detail_service;


@Service
public class Order_Detail_impl implements Order_Detail_service{

	@Autowired
	OrderDetailDao dao;

	@Override
	public List<Product_option> findAllOptionProductByIduser(String idUser, String id) {
		return dao.findAllOptionProductByIduser(idUser, id);
	}
	



}
