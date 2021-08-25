package com.ede.edecustomerservice.implement;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ede.edecustomerservice.entity.User;
import com.ede.edecustomerservice.service.Auth_Service;
import com.fasterxml.jackson.databind.JsonNode;
@Service
public class Auth_impl implements Auth_Service {

	@Override
	public User getUserLogin(String headers) {
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		header.add("Authorization", "Bearer " + headers);

		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/ede-oauth-service/api/auth/check/login";
		HttpEntity<Object> entity = new HttpEntity<Object>(null, header);
		System.out.println(url + "" + entity);
		ResponseEntity<JsonNode> respone = restTemplate.exchange(url, HttpMethod.POST, entity, JsonNode.class);
		JsonNode jsonNode = respone.getBody();
		String url2 = "http://localhost:8080/ede-customer/findbyusername/" + jsonNode.get("id");
		ResponseEntity<User> user = restTemplate.exchange(url2, HttpMethod.GET, entity, User.class);
		return user.getBody();
	}

}
