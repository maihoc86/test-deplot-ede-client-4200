package com.ede.edecustomerservice.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "shiper_partner")
@NoArgsConstructor
@AllArgsConstructor
public class ShiperPartner implements Serializable{
	@Id
	String id;
	String name;
	String api;
}
