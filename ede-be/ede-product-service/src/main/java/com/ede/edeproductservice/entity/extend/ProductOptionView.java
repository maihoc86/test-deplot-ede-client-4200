/**
 * @author thái học
 *
 * 
 */
package com.ede.edeproductservice.entity.extend;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.entity.Product_discount;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author thái học
 *
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "view_product_option")
@SecondaryTable(name = "product_option", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
public class ProductOptionView {
	@Id
	String id;

	@Column(table = "product_option")
	String display_name;
	@Column(table = "product_option")
	Double price;
	@Column(table = "product_option")
	String size;
	@Column(table = "product_option")
	Integer quantity;
	@Column(table = "product_option")
	Boolean is_delete;
	@Column(table =  "product_option")
	Double weight;
	@ManyToOne
	@JoinColumn(name = "id_product", table = "product_option")
	Product product;
	
	@OneToMany( mappedBy = "productdiscount")
	List<Product_discount> productDiscount;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "productoption")
	@Fetch(value = FetchMode.SUBSELECT)
	List<ProductOptionImageView> product_option_images;

	@Override
	public String toString() {
		return "";
	}

	@Transient
	public ProductOptionImageView optionImageDef;

	public ProductOptionImageView getOptionImageDef() {
		return this.getProduct_option_images().get(0);
	}
	
	
	
}
