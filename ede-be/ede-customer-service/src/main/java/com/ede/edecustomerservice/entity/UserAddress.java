package com.ede.edecustomerservice.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
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
public class UserAddress implements Serializable{
	@Id
	String id;
	String user_id;
	String adress;
}
