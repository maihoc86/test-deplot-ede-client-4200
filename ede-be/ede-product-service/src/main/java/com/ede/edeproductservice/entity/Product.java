package com.ede.edeproductservice.entity;

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

@Data
@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Product {
	
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
//	@Fetch(value = FetchMode.SUBSELECT)
	List<Product_option> product_options;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "producttag")
	@Fetch(value = FetchMode.SUBSELECT)
	List<Product_tag> product_tags;

	@Override
	public String toString() {
		return String.format("%s | %s | %s | %s | %s | %s", this.id, this.origin, this.description, this.name,
				this.enable, this.deleted);
	}
}
