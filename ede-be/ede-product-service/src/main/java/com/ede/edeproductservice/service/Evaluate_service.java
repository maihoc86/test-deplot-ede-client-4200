package com.ede.edeproductservice.service;

import java.util.List;

import com.ede.edeproductservice.entity.Evaluate;
import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.entity.User;

public interface Evaluate_service {

	Evaluate save(Evaluate comment);

	List<Evaluate> findByUserAndProduct(User findById, Product findById2);

}
