package com.ede.edeorderservice.service;

import java.util.List;

import com.ede.edeorderservice.entity.Order;

public interface Order_service {

	/**
	 * @author thái học
	 *
	 * 
	 */
	List<Order> searchOrderStatus(String keyword, String string);

	/**
	 * @author thái học
	 *
	 * 
	 */
	List<Order> searchOrderAll(String keyword);

}
