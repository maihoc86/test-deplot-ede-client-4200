package com.ede.edecustomerservice.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ede.edecustomerservice.dao.ReceiveDao;
import com.ede.edecustomerservice.entity.Receive_news;
import com.ede.edecustomerservice.service.Receive_news_Service;
@Service
public class ReceiveNewsImpl implements Receive_news_Service{
	
	@Autowired
	ReceiveDao dao;

	@Override
	public Receive_news findEmail(String email) {
		return dao.findEmail(email);
	}

}
