package com.ede.edeproductservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ede.edeproductservice.entity.User;

public interface UserDao  extends JpaRepository<User, String>{

}
