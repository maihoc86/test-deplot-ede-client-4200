package com.ede.edecustomerservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ede.edecustomerservice.entity.Cart;
import com.ede.edecustomerservice.entity.Cart_item;
import com.ede.edecustomerservice.entity.User;

public interface CartDao extends JpaRepository<Cart, String> {
	@Query("select o from Cart o where o.user.id=?1 ")
	Cart findByUserid(String user);
	@Query("select o.cart_items from Cart o where o.user.id=?1 ")
	List<Object> getAllItemByUserID(String id);

}
