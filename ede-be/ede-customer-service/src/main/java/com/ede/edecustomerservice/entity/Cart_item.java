package com.ede.edecustomerservice.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "cart", uniqueConstraints = { @UniqueConstraint(columnNames = { "id_cart", "id_product_option" }) })
@AllArgsConstructor
@NoArgsConstructor
public class Cart_item implements Serializable {
	@Id
	String id;
	int quantity;

	@ManyToOne
	@JoinColumn(name = "id_cart")
	Cart cart;

	@ManyToOne
	@JoinColumn(name = "id_product_option")
	Product_option product_option;
}
