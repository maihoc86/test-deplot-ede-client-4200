package com.ede.edeproductservice.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ede.edeproductservice.dao.OrderDao;
import com.ede.edeproductservice.entity.Evaluate;
import com.ede.edeproductservice.entity.Product_option;
import com.ede.edeproductservice.service.Order_Service;


@Service
public class Order_impl implements Order_Service{

	@Autowired
	OrderDao dao;
	
	
	

}
