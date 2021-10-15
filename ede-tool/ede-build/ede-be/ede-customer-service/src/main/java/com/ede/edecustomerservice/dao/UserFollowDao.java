package com.ede.edecustomerservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ede.edecustomerservice.entity.UserFollow;

public interface UserFollowDao extends JpaRepository<UserFollow, String> {

}
