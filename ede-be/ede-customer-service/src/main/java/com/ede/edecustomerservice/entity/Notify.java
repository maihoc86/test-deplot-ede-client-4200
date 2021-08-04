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
@Table(name = "notify")
public class notify {
	@Id
	String id;
	String user_id;
	String title;
	String content;
	String image;
	Date create_date;
	Boolean is_see;
}
