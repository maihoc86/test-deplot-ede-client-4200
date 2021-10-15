/**
 * @author thái học
 *
 * 
 */
package com.ede.edeproductservice.entity.extend;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.ede.edeproductservice.entity.Product;

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
@Table(name = "view_product_discount")
@SecondaryTable(name = "product_discount", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
public class ProductDiscountView {
	@Id
	String id;

	@Column(table = "product_discount")
	Double discount;
	@Column(table = "product_discount")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date startdate;
	@Column(table = "product_discount")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date enddate;
	@Column(table = "product_discount")
	Boolean status;
	@ManyToOne
	@JoinColumn(name = "id_product", table = "product_discount")
	Product productdiscount;

}
