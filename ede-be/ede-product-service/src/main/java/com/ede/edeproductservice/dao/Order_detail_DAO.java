package com.ede.edeproductservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ede.edeproductservice.entity.Orderdetail;
import com.ede.edeproductservice.service.Order_detail_service;

public interface Order_detail_DAO extends JpaRepository<Orderdetail, String> {

	
	@Query("SELECT p FROM Orderdetail p where p.productOption.id =:id")
	Orderdetail findProductOption(String id);

}
