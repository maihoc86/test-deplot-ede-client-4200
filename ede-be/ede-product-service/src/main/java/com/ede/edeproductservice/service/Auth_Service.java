package com.ede.edeproductservice.service;
import com.ede.edeproductservice.entity.Shop;
import com.ede.edeproductservice.entity.User;


public interface Auth_Service {
	User getUserLogin(String headers);

	Shop getShopLogin(String header);
	
}
