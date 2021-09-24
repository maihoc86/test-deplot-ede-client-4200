package com.ede.edeorderservice.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ede.edeorderservice.entity.Order_Discount;

public interface OrderDiscountDao extends JpaRepository<Order_Discount, String> {

	@Query("Select o from Order_Discount o where status = true")
	List<Order_Discount> listAllStatusTrue();

	@Query("Select o from Order_Discount o where todate =:todate and enddate=:enddate")
	List<Order_Discount> findOrderDiscountDate(Date todate, Date enddate);

}
