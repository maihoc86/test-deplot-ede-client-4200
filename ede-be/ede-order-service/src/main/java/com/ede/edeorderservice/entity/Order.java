package com.ede.edeorderservice.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
	String id_order;
	String phone;
	String status;
	Date create_date;
	String discount_code;
	double total_amount;
	String note;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "order")
	List<Order_Detail> order_detail;
	
	@Override
	public String toString() {
		return "";
	}
	
	
	
}
