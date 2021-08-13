package com.ede.edeproductservice.entity;

import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

public class Product_parent_category {
	@Id
	String id;
	String name;
	String image_url;
	Boolean is_enable;
	Boolean is_delete;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "product_child_category")
	List<Product_parent_child_category> product_parent_child_category;
}
