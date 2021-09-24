package com.ede.edeorderservice.service;

import java.util.Date;
import java.util.List;

import com.ede.edeorderservice.entity.Order_Discount;

public interface Order_discount_service {

	Order_Discount save(Order_Discount order_Discount);

	List<Order_Discount> listAll();

	Order_Discount findById(String id);

	Order_Discount deleteById(String id);

	List<Order_Discount> listAllStatusTrue();

	List<Order_Discount> findOrderDiscountDate(Date todate, Date enddate);
	
}
