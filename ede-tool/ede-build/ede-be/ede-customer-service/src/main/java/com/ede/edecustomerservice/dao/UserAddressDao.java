package com.ede.edecustomerservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ede.edecustomerservice.entity.UserAddress;

public interface UserAddressDao extends JpaRepository<UserAddress, String>{

}
