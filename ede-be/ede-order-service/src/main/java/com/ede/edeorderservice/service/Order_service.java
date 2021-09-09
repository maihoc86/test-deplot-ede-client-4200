package com.ede.edeorderservice.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

	/**
	 * @author thái học
	 *
	 * 
	 */
	List<Order> findAll();

	/**
	 * @author thái học
	 * @param page 
	 *
	 * 
	 */
	Page<Order> findAllOrderByShop(String id, Pageable page);

	/**
	 * @author thái học
	 * @param pageRequest 
	 *
	 * 
	 */
	Page<Order> findAllOrderShopByStatus(String string, String status, Pageable page);

}
