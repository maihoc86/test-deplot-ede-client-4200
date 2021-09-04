/**
 * @author thái học
 *
 * 
 */
package com.ede.edeproductservice.entity.extend;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import com.ede.edeproductservice.entity.Product_option;

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
@Table(name = "view_product_option_image")
@SecondaryTable(name = "product_option_image", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
public class ProductOptionImageView {
	@Id
	String id;
	@Column(table = "product_option_image")
	String image;

	@ManyToOne
	@JoinColumn(name = "id_product_option", table = "product_option_image")
	Product_option productoption;

}
