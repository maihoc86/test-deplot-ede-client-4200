package com.ede.edeproductservice.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "evaluate")
@AllArgsConstructor
@NoArgsConstructor
public class Evaluate implements Serializable{

	
	@Id
	String id;
	int rate;
	String content;
	Date date;
	
	@ManyToOne
	@JoinColumn(name="id_user")
	User user;
	
	@ManyToOne
	@JoinColumn(name = "id_product")
	Product product;
}
