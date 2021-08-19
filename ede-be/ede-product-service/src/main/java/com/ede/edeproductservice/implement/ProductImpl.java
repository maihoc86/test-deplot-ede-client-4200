package com.ede.edeproductservice.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ede.edeproductservice.dao.ProductDao;
import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.service.ProductService;


@Service
public class ProductImpl implements ProductService {

	@Autowired
	ProductDao dao;

	@Override
	public List<Product> findAll() {
		return dao.findAll();
	}

	@Override
	public List<Product> findAllIsdeleteFalse() {
		
		return dao.findAllByDeleted(false);
	}

}
