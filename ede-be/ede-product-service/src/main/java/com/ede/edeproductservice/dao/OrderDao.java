package com.ede.edeproductservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ede.edeproductservice.entity.Order;
import com.ede.edeproductservice.entity.Product_option;



public interface OrderDao extends JpaRepository<Order, String> {




}
