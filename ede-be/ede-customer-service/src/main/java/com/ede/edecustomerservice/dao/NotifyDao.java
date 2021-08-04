package com.ede.edecustomerservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ede.edecustomerservice.entity.Notify;

public interface NotifyDao extends JpaRepository<Notify, String> {

}
