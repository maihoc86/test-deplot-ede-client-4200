package com.ede.edeproductservice.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ede.edeproductservice.dao.EvaluateDao;
import com.ede.edeproductservice.entity.Evaluate;
import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.entity.User;
import com.ede.edeproductservice.service.Evaluate_service;

@Service
public class Evaluate_impl implements Evaluate_service{

	@Autowired
	EvaluateDao dao;
	
	
	@Override
	public Evaluate save(Evaluate comment) {
		return dao.save(comment);
	}


	@Override
	public List<Evaluate> findByUserAndProduct(User findById, Product findById2) {
		return dao.findByUserAndProduct(findById, findById2);
	}

}
