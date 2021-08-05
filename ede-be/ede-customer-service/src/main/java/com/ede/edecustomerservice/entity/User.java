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
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Entity 
@Table(name = "users")
public class User implements Serializable{
	@Id
	String id;
	String username;
	String password;
	String first_name;
	String last_name;
	String email;
	String photo;
	String gender;
	String address;
	String phone;
	Boolean is_delete;
	String role;
	String otp;
	
}
