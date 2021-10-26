package com.ede.edecustomerservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "product_tag")
@AllArgsConstructor
@NoArgsConstructor
public class Product_tag {

	@Id
	String id;
	String tag;
	
	@ManyToOne
	@JoinColumn(name = "id_product")
	Product producttag;

}
