package com.ede.edeproductservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "order_detail")
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Orderdetail {

	@Id
	String id;
	double price;
	int quantity;

	@ManyToOne
	@JoinColumn(name = "id_product_option")
	Product_option productOption;

	@ManyToOne
	@JoinColumn(name = "orderid")
	Order order;

}
