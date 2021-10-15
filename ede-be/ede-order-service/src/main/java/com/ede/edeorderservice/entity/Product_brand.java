package com.ede.edeorderservice.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "brand")
	List<Product> product;
}
