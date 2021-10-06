package com.ede.edeproductservice.entity.extend;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.ede.edeproductservice.entity.Product_brand;
import com.ede.edeproductservice.entity.Product_child_category;
import com.ede.edeproductservice.entity.Product_discount;
import com.ede.edeproductservice.entity.Product_tag;
import com.ede.edeproductservice.entity.Shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "view_product_search")
@SecondaryTable(name = "product", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
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
	@Column(table = "product")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date createdate;
	
	// ------------------------
	@ManyToOne
	@JoinColumn(name = "id_shop", table = "product")
	Shop shop;
	
	@ManyToOne
	@JoinColumn(name = "id_brand", table = "product")
	Product_brand brand;
	
	@ManyToOne
	@JoinColumn(name = "id_category", table = "product")
	Product_child_category childCategory;
	// ------------------------


	@OneToMany(mappedBy = "product")
	List<ProductOptionView> productOptions;


	@OneToMany(mappedBy = "productdiscount")
	List<Product_discount> productDiscount;


	@OneToMany(mappedBy = "producttag")
	List<Product_tag> producTags;
	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	@Transient
	public ProductOptionView optionDef;

	public ProductOptionView getOptionDef() {
		return this.getProductOptions().get(0);
	}

//	@Transient
//	public Product_discount discountDef;
//
//	public Product_discount getDiscountDef() {
//		if (this.getProductOptions() != null) {
//			return this.getProductDiscount().get(0);
//		} else {
//			System.err.println(this.getProductDiscount());
//			return null;
//		}
//
//	}

}
