package com.ede.edeorderservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ede.edeorderservice.entity.Shop;
import com.ede.edeorderservice.entity.User;

public interface ShopDao extends JpaRepository<Shop, String> {
	//@Query("select o from Shop where o.user.id=?1")
	Shop findByUser(User us);

//	Shop finByIdUser(String id);	

}
