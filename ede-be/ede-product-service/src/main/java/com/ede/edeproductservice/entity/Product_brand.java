package com.ede.edeproductservice.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "product_brand")
@AllArgsConstructor
@NoArgsConstructor
public class Product_brand {

	@Id
	String id;
	String name;
	String avatar;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "brand")
	List<Product> product;
}
