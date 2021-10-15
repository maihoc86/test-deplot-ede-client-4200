package com.ede.edecustomerservice.service;

import org.springframework.stereotype.Service;

/**
 * Service token manager
 * @author vinh
 *
 */
@Service
public interface JsonWebTokenService {

	boolean checkToken(String token);

	String getValue(String token);

	/**
	 * Create Time 
	 * @param subject such as content of token
	 * @param timeLife miliseccon life time of token from present. 
	 * @return
	 */
	String createToken(String subject, long lifeTime);

}
