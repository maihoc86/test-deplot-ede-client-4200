package com.ede.edeorderservice.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "evalue")
@AllArgsConstructor
@NoArgsConstructor
public class Evaluated implements  Serializable{
	
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
