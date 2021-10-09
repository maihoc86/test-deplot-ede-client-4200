package com.ede.edeorderservice.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

	@Id
	String id;
	String phone;
	String status;
	Date create_date;
	double total_amount;
	String note;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "order")
	@Fetch(value = FetchMode.SUBSELECT)
	List<Orderdetail> order_detail;
	
	@ManyToOne
	@JoinColumn(name="id_user")
	User user;
	
	@ManyToOne
	@JoinColumn(name="id_discount")
	Order_Discount discount;
	
	@Override
	public String toString() {
		return "";
	}
	
	
	
}
