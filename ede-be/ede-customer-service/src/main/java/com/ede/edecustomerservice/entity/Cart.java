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
@Table(name = "cart")
public class Cart {
	@Id
	String id;
	String user_id;
	String product_id;
	Integer quantity;

}
