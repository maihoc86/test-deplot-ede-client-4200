package com.ede.edecustomerservice.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ede.edecustomerservice.entity.Cart_item;

public interface CartItemDao extends JpaRepository<Cart_item, String> {
	@Transactional
	@Modifying
	@Query("delete from Cart_item o where o.cart.id = ?1")
	void deleteByCartID(String id);
	@Query("select o from Cart_item o where o.cart.user.id = ?1")
	List<Cart_item> getAllByUserID(String id);

}
