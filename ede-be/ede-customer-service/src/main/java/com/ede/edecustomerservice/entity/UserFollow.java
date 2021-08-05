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
@Table(name = "user_follow")
@NoArgsConstructor
@AllArgsConstructor
public class UserFollow implements Serializable{
	@Id
	String id;
	String user_id;
	String id_shop;
	Boolean evalute_status;
}
