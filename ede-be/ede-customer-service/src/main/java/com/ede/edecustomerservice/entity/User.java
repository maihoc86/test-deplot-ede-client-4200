package com.ede.edecustomerservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity 
@Table(name = "user")
public class User {
	@Id
	String username;
	String password;
	String first_name;
	String last_name;
	String email;
	String gender;
	String address;
	String phone;
	Boolean is_delete;
	Boolean role;
	String otp;
	
}
