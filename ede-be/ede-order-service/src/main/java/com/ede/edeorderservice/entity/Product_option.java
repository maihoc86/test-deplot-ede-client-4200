package com.ede.edeorderservice.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
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
@Data
@Entity
@Table(name = "product_option")
@AllArgsConstructor
@NoArgsConstructor
public class Product_option implements Serializable {
	@Id
	String id;
	String display_name;
	Double price;
	String size;
	Integer quantity;

	@ManyToOne
	@JoinColumn(name = "id_product")
	Product product;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "productoption")
	@Fetch(value = FetchMode.SUBSELECT)
	List<Product_option_image> product_option_images;

	
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "productOption")
	@Fetch(value = FetchMode.SUBSELECT)
	List<Orderdetail> orderdetail;
	
	@Override
	public String toString() {
		return "";
	}
}
