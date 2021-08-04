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
@Table(name = "user_address")
public class UserAddress {
	@Id
	String id;
	String user_id;
	String adress;
}
