package com.ede.edeproductservice.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
@SuppressWarnings("serial")
@Entity
@Data
public class Roles {
	@Id
	private String id;
	private String name;
	@JsonIgnore
	@OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
	List<Authorities> authorities;
	
	@Override
	public String toString() {
		return "";
	}
}
