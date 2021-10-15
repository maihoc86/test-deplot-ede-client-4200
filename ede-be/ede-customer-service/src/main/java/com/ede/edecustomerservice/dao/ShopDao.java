package com.ede.edecustomerservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ede.edecustomerservice.entity.Shop;
import com.ede.edecustomerservice.entity.User;

public interface ShopDao extends JpaRepository<Shop, String>{

	Shop findByUser(User us);
	@Query("select o from Shop o where o.name like ?1")
	List<Shop> findAllByName(String name);

}
