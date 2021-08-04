package com.ede.edecustomerservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "shiper_shop")
public class ShiperShop {
	@Id
	String id;
	String user_id;
	String id_shop;
}
