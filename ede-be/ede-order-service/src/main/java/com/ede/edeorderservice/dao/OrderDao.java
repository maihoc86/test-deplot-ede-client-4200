package com.ede.edeorderservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ede.edeorderservice.entity.Order;

public interface OrderDao extends JpaRepository<Order, String> {

}
