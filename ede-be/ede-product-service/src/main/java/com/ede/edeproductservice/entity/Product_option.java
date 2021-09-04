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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Entity
@Data
@Table(name = "product_option")
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Product_option implements Serializable {
	@Id
	String id;
	String display_name;
	Double price;
	String size;
	Integer quantity;
	Boolean is_delete;

	@ManyToOne
	@JoinColumn(name = "id_product")
	Product product;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "productoption")
	@Fetch(value = FetchMode.SUBSELECT) 
	List<Product_option_image> product_option_images;
	
	
	@Override
	public String toString() {
		return "";
	}
//	/**
//	 * @return the product
//	 */
//	public Product getProduct() {
//		return product;
//	}
//	/**
//	 * @param product the product to set
//	 */
//	public void setProduct(Product product) {
//		this.product = product;
//	}
//	@Transient
//	public Product_option_image optionImageDef;
//	public Product_option_image getOptionImageDef() {
//		return this.getProduct_option_images().get(0);
//	}
	
}
