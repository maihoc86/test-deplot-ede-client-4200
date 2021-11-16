package com.ede.edecustomerservice.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "receive_news")
@AllArgsConstructor
@NoArgsConstructor
public class Receive_news {
	@Id
	String id;
	String email;
	Date create_date = new Date();
}
