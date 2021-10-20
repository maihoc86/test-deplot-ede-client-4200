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
import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "view_product_evaluate")
@SecondaryTable(name = "evaluate", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
public class ProductEvaluateView {
	@Id
	String id;

	@Column(table = "evaluate")
	int rate;
	@Column(table = "evaluate")
	String content;
	@Column(table = "evaluate")
	Date date;
	@ManyToOne
	@JoinColumn(name = "id_user", table = "evaluate")
	User user;

	@ManyToOne
	@JoinColumn(name = "id_product", table = "evaluate")
	Product product;
}
