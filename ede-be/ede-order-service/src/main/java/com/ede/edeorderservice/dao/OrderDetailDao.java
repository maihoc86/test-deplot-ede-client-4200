package com.ede.edeorderservice.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ede.edeorderservice.entity.Order_Detail;

public interface OrderDetailDao extends JpaRepository<Order_Detail, String>{

	@Query("select sum(o.quantity) from Order_Detail o where o.product.id =?1 ")
	Long getCountProductOder(String id);

}
