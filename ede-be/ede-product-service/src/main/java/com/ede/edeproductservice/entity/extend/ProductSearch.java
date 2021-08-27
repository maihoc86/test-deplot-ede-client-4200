package com.ede.edeproductservice.entity.extend;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import com.ede.edeproductservice.entity.Product_brand;
import com.ede.edeproductservice.entity.Product_child_category;
import com.ede.edeproductservice.entity.Product_discount;
import com.ede.edeproductservice.entity.Product_option;
import com.ede.edeproductservice.entity.Product_tag;
import com.ede.edeproductservice.entity.Shop;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "view_product_search")
@SecondaryTable(name = "product",  pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ProductSearch {
	
	@Id
	String idProduct;
	
	private String keysearch;

	@Column(table = "product")
	String origin;
	@Column(table = "product")
	String description;
	@Column(table = "product")
	String name;
	@Column(table = "product")
	Boolean enable;
	@Column(table = "product")
	Boolean deleted;
	@Column(table = "product")
	String location;
	//------------------------
	@ManyToOne
	@JoinColumn(name = "id_shop", table = "product")
	Shop shop;
	@ManyToOne
	@JoinColumn(name = "id_brand", table = "product")
	Product_brand brand;
	@ManyToOne
	@JoinColumn(name = "id_category", table = "product")
	Product_child_category childCategory;
	//------------------------
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<Product_option> productOptions;
	@JsonIgnore
	@OneToMany(mappedBy = "productdiscount")
	List<Product_discount> productDiscount;
	@JsonIgnore
	@OneToMany(mappedBy = "producttag")
	List<Product_tag> producTags;
	
}
