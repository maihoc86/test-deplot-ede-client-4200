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
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Product {
	@Id
	String id;
	String origin;
	String description;
	String name;
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
