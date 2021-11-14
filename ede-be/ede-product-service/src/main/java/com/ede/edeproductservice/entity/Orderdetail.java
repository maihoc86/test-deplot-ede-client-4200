package com.ede.edeproductservice.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "order_detail", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "id_product_option", "orderid" }) })
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Orderdetail implements Serializable {

	@Id
	String id;
	double price;
	int quantity;
	double fee_ship;

	@ManyToOne
	@JoinColumn(name = "id_product_option")
	Product_option productOption;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "orderid")
	Order order;

}
