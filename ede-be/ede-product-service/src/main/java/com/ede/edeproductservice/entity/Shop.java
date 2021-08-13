package com.ede.edeproductservice.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "shop", uniqueConstraints = { @UniqueConstraint(columnNames = "user_id") })
@AllArgsConstructor
@NoArgsConstructor
public class Shop implements Serializable {
	@Id
	String id;
	String name;
	String image;
	Date create_date;
	String address;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "shop")
	List<Product> product;
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

}
