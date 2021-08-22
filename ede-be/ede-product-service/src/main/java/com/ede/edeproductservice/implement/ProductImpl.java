package com.ede.edeproductservice.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
	public Page<Product> searchByKeysearch(String keysearch, PageRequest pageRequest) {
		return this.dao.searchBykeysearch(keysearch, pageRequest);
	}

	@Override
	public Product save(Product product) {
		return dao.save(product);
	}

	@Override
	public List<Product> findAllIsdeleteFalse() {	
		return dao.findAllByDeleted(false);
	}

	@Override
	public Product findById(String id) {	
		return dao.findById(id).get();
	}

}
