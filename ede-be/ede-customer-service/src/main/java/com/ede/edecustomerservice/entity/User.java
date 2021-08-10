package com.ede.edecustomerservice.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "username") })
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
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
	Boolean is_active;
	String role;
	String otp;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	List<Authorities> authorities;

	@Override
	public String toString() {
		return "";
	}
}
