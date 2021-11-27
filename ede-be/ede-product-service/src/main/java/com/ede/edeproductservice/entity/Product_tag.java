package com.ede.edeproductservice.entity;

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

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "tag")
	@Fetch(value = FetchMode.SUBSELECT)
	List<Product_tag_search> product_tag_searchs;

	@Override
	public String toString() {
		return "";
	}
}
