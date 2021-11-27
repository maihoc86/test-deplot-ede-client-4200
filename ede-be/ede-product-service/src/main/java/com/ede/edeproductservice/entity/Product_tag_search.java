package com.ede.edeproductservice.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "product_tag_search")
@AllArgsConstructor
@NoArgsConstructor
public class Product_tag_search {

	@Id
	String id;
	String cookie;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date date_view;

	@ManyToOne
	@JoinColumn(name = "id_tag")
	Product_tag tag;

}
