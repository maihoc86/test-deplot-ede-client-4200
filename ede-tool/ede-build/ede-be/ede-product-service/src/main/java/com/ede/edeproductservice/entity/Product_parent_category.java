package com.ede.edeproductservice.entity;

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
@Table(name = "product_parent_category")
@AllArgsConstructor
@NoArgsConstructor
public class Product_parent_category {
	@Id
	String id;
	String name;
	String image_url;
	Boolean is_enable;
	
	Boolean is_delete;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "parentcategory")
	List<Product_parent_child_category> productParentChildCategory;

	@Override
	public String toString() {
		return String.format("%s | %s | %s | %s | %s | %s  ", this.id, this.name, this.image_url, this.is_enable,
				this.is_enable, this.is_delete);
	}
}
