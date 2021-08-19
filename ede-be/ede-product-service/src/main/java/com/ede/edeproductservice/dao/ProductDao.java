package com.ede.edeproductservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ede.edeproductservice.entity.Product;

public interface ProductDao extends JpaRepository<Product, String> {

	List<Product> findAllByDeleted(boolean isdelete);

}
