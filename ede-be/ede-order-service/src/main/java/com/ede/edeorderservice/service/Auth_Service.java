package com.ede.edeorderservice.service;
import org.springframework.web.client.HttpClientErrorException;

import com.ede.edeorderservice.entity.Shop;
import com.ede.edeorderservice.entity.User;



public interface Auth_Service {
	User getUserLogin(String headers);

	Shop getShopLogin(String header);

	void Security(String token, StringBuffer requestURL) throws HttpClientErrorException;
	
}
