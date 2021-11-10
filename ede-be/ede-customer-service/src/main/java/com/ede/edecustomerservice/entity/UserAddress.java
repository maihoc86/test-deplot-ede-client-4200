package com.ede.edecustomerservice.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "user_address")
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress implements Serializable {
	@Id
	String id;
	String first_name;
	String last_name;
	String phone;
	String address;

	@ManyToOne
	@JoinColumn(name = "user_id")
	User user;

}
