package com.ede.edeorderservice.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity
@Table(name = "order")
@AllArgsConstructor
@NoArgsConstructor
public class Order {

	@Id
	String id;
	String phone;
	String status;
	Date create_date;
	String discount_code;
	double total_amount;
	String note;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "order")
	List<Order_Detail> order_detail;
	
	@ManyToOne
	@JoinColumn(name="id_user")
	User user;
	
	
	@Override
	public String toString() {
		return "";
	}
	
	
	
}
