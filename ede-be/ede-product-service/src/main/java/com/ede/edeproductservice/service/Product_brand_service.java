package com.ede.edeproductservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ede.edeproductservice.dao.Product_brand_Dao;
import com.ede.edeproductservice.entity.Product_brand;

public interface Product_brand_service {


	List<Product_brand> findAll();

}
