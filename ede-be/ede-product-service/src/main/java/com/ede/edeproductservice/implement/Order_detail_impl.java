package com.ede.edeproductservice.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ede.edeproductservice.dao.Order_detail_DAO;
import com.ede.edeproductservice.entity.Orderdetail;
import com.ede.edeproductservice.entity.Product_option;
import com.ede.edeproductservice.entity.extend.ProductSearch;
import com.ede.edeproductservice.service.Order_detail_service;
@Service
public class Order_detail_impl implements Order_detail_service {
	@Autowired
	Order_detail_DAO dao;
	
	@Override
	public List<Product_option> findAllOptionProductByIduser(String idUser, String id) {
		return dao.findAllOptionProductByIduser(idUser, id);
	}
	@Override
	public Orderdetail findProductOption(String id) {
		return dao.findProductOption(id);
	}
	@Override
	public List<Object> findAllOptionProductInOrderDetailByIdShop(String id) {
		return dao.findAllOptionProductInOrderDetailByIdShop(id);
	}
	@Override
	public List<Object> findAllOptionProductInOrderDetail() {
		return dao.findAllOptionProductInOrderDetail();
	}
	
}
