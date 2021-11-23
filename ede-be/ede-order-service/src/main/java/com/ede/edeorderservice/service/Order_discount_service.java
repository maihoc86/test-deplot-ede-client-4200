package com.ede.edeorderservice.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.ede.edeorderservice.entity.Order;
import com.ede.edeorderservice.entity.Order_Discount;

public interface Order_discount_service {

	Order_Discount save(Order_Discount order_Discount);

	List<Order_Discount> listAll();

	Order_Discount findById(String id);

	Order_Discount deleteById(String id);

	Order_Discount findOrderDiscountDate(Date todate);

	Page<Order_Discount> listAllStatusTrue(PageRequest of);

	Page<Order_Discount> listAllFilter(String searchTuNgayDate, String searchDenNgayDate, PageRequest of);


	
}
