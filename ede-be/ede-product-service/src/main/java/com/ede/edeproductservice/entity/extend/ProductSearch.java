package com.ede.edeproductservice.entity.extend;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ede.edeproductservice.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "view_product_search")
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearch extends Product{

	private String keysearch;
	
}
