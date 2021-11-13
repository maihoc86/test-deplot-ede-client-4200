package com.ede.edecustomerservice.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "cart")
@AllArgsConstructor
@NoArgsConstructor
public class Cart implements Serializable {
	@Id
	String id;

	@OneToOne
	@JoinColumn(name = "user_id")
	User user;

	@JsonIgnore
	@OneToMany(mappedBy = "cart")
	List<Cart_item> cart_items;
	
	
	@Override
	public String toString() {
		return "";
	}
}
