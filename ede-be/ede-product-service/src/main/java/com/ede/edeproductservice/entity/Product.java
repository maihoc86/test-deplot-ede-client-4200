package com.ede.edeproductservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	@Id
	String id;
	String origin;
	String description;
	Boolean enable;
	Boolean deleted;
	
	@ManyToOne
	@JoinColumn(name = "id_shop")
	Shop shop;
	@ManyToOne
	@JoinColumn(name = "id_brand")
	Product_brand brand;
	@ManyToOne
	@JoinColumn(name = "id_category")
	Product_child_category child_category;

}
