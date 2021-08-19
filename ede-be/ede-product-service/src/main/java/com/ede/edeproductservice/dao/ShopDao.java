package com.ede.edeproductservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ede.edeproductservice.entity.Shop;
import com.ede.edeproductservice.entity.User;

public interface ShopDao extends JpaRepository<Shop, String> {
	//@Query("select o from Shop where o.user.id=?1")
	Shop findByUser(User us);	

}
