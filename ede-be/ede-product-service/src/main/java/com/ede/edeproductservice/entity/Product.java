package com.ede.edeproductservice.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Product implements Serializable {
	@Id
	String id;
	String origin;
	String description;
	String name;
	Boolean enable;
	Boolean deleted;
	String location;

	@ManyToOne
	@JoinColumn(name = "id_shop")
	Shop shop;
	@ManyToOne
	@JoinColumn(name = "id_brand")
	Product_brand brand;
	@ManyToOne
	@JoinColumn(name = "id_category")
	Product_child_category child_category;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
	List<Product_option> product_options;

	@JsonIgnore
	@OneToMany( mappedBy = "productdiscount")
	List<Product_discount> product_discount;

	@JsonIgnore
	@OneToMany(mappedBy = "producttag")
	List<Product_tag> product_tags;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<Evaluate> evaluate;

	@Override
	public String toString() {
		return "";
	}
//
//	@Transient
//	public Product_option optionDef;
//
//	@Transient
//	public Product_option getOptionDef() {
//		if (this.getProduct_options() != null) {
//			return this.getProduct_options().get(0);
//		} else {
//			return null;
//		}
//	}

}
