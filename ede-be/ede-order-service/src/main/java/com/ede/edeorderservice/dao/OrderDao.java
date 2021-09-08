package com.ede.edeorderservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ede.edeorderservice.entity.Order;

public interface OrderDao extends JpaRepository<Order, String> {

	/**
	 * @author thái học
	 *
	 * 
	 */
	@Query("Select o FROM Order o where o.status=:status and o.phone Like %:keyword% or o.create_date Like %:keyword% or o.user.username Like %:keyword%")
	List<Order> searchOrderStatus(String keyword, String status);

	/**
	 * @author thái học
	 *
	 * 
	 */
	@Query("Select o FROM Order o where o.phone Like %:keyword% or o.create_date Like %:keyword% or o.user.username Like %:keyword% ")
	List<Order> searchOrderAll(String keyword);

}
