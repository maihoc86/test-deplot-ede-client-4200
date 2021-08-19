package com.ede.edeproductservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ede.edeproductservice.entity.Shop;

public interface ShopDao extends JpaRepository<Shop, String> {

}
