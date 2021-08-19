package com.ede.edeproductservice.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "product_option")
@AllArgsConstructor
@NoArgsConstructor
public class Product_option implements Serializable {
	@Id
	String id;

	@ManyToOne
	@JoinColumn(name = "id_product")
	Product product;
}
