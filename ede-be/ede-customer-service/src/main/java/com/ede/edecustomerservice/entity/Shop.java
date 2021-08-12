package com.ede.edecustomerservice.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "shop")
@AllArgsConstructor
@NoArgsConstructor
public class Shop implements Serializable {
	@Id
	String id;
	String image;
	Date create_date;
	String address;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

}
