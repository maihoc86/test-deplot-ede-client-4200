package com.ede.edeproductservice.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ede.edeproductservice.dao.Product_tag_search_Dao;
import com.ede.edeproductservice.service.Product_Tag_search_service;

@Service
public class Product_tag_search_impl implements Product_Tag_search_service {

	@Autowired
	Product_tag_search_Dao dao;

	@Override
	public List<Object> getTop10Tag() {
		return dao.getTop10Tag();
	}

}
