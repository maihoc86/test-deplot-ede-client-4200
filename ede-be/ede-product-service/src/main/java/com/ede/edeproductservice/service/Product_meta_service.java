package com.ede.edeproductservice.service;

import java.sql.Date;

import com.ede.edeproductservice.entity.Product_meta;

public interface Product_meta_service {

	Product_meta save(Product_meta product_meta);

	Product_meta findByIdUser(String idUser, String idProduct,Date date);

}
