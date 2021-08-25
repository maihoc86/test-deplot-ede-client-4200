package com.ede.edeproductservice.service;
import com.ede.edeproductservice.entity.User;


public interface Auth_Service {
	User getUserLogin(String headers);

}
