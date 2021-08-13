package com.ede.edeproductservice.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "product_child_category")
@AllArgsConstructor
@NoArgsConstructor
public class Product_child_category {

	@Id
	String id;
	String name;
	String image_url;
	Boolean is_enable;
	Boolean is_delete;

	@ManyToOne
	@JoinColumn(name = "id_parent")
	Product_parent_child_category child_parentCategory;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "child_category")
	List<Product> product;
}
