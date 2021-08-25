package com.ede.edeorderservice.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
	String otp;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	@Fetch(value = FetchMode.SUBSELECT)
	List<Authorities> authorities;

	
	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "id_user")
	Order_Detail order_detail;
	
	
	@Override
	public String toString() {
		return String.format("%s | %s | %s | %s | %s | %s | %s | %s | %s | %s | %s | %s ",
				this.id, this.username, this.password, this.first_name, this.last_name,
				this.email, this.photo, this.address, this.phone, this.is_delete,
				this.is_active, this.otp);
	}
}
