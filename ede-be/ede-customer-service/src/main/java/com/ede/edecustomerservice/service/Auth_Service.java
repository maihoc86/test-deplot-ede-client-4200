package com.ede.edecustomerservice.service;

import com.ede.edecustomerservice.entity.User;

public interface Auth_Service {
	User getUserLogin(String headers);

}
