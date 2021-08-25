package com.ede.edeorderservice.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
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
	String id_product_option;
	String id_user;
	double price;
	int quantity;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "id_product_option")
	Product_option product_option;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "user")
	User user;

	@Override
	public String toString() {
		return "";
	}
	
	
}
