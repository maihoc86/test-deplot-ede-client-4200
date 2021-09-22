package com.ede.edecustomerservice.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "shop", uniqueConstraints = { @UniqueConstraint(columnNames =  "user_id") })
@AllArgsConstructor
@NoArgsConstructor
public class Shop implements Serializable {
	@Id
	String id;
	String name;
	String image;
	String image_sub;
	Date create_date = new Date();
	String address;
	String description;
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

}
