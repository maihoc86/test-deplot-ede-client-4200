package com.ede.edeproductservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ede.edeproductservice.entity.Evaluate;
import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.entity.User;

public interface EvaluateDao extends JpaRepository<Evaluate, String> {

	List<Evaluate> findByUserAndProduct(User findById, Product findById2);

	

}
