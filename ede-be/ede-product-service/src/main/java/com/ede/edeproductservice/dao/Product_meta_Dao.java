package com.ede.edeproductservice.dao;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ede.edeproductservice.entity.Product_meta;

public interface Product_meta_Dao extends JpaRepository<Product_meta, String> {

	@Query("SELECT p FROM Product_meta p where p.user.id =:idUser and p.product.id =:idProduct and p.date_view =:date")
	Product_meta findByIdUser(String idUser, String idProduct, Date date);

}
