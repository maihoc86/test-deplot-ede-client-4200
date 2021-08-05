package com.ede.edecustomerservice.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "notify")
@NoArgsConstructor
@AllArgsConstructor
public class Notify implements Serializable{
	@Id
	String id;
	String user_id;
	String title;
	String content;
	String image;
	Date create_date;
	Boolean is_see;
}
