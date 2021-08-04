package com.ede.edecustomerservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "shiper_partner")
public class ShiperPartner {
	@Id
	String id;
	String name;
	String api;
}
