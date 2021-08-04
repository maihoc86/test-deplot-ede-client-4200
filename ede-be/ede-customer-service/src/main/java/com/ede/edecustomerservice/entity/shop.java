package com.ede.edecustomerservice.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "shop")
public class shop {
	@Id
	String id;
	@ManyToOne
	String user_id;
	String image;
	Date create_date;
	String address;
}
