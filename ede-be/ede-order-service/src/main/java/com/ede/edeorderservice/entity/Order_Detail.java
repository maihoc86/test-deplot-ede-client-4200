package com.ede.edeorderservice.entity;

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
@Table(name="order_detail")
@AllArgsConstructor
@NoArgsConstructor
public class Order_Detail {

	@Id
	String id;
	double price;
	int quantity;
	
	
	@ManyToOne
	@JoinColumn(name="id_product_option")
	Product_option productOption;

	@ManyToOne
	@JoinColumn(name="id_order")
	Order order;
	
	

	@Override
	public String toString() {
		return "";
	}
	
	
}
