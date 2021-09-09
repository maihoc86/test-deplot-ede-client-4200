package com.ede.edeorderservice.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ede.edeorderservice.dao.ShopDao;
import com.ede.edeorderservice.entity.Shop;
import com.ede.edeorderservice.entity.User;
import com.ede.edeorderservice.service.Auth_Service;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class Auth_impl implements Auth_Service {

	@Autowired
	ShopDao shopdao;

	@Override
	public User getUserLogin(String headers) {
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		header.add("Authorization", "Bearer " + headers);

		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/ede-oauth-service/api/auth/check/login";
		HttpEntity<Object> entity = new HttpEntity<Object>(null, header);
		ResponseEntity<JsonNode> respone = restTemplate.exchange(url, HttpMethod.POST, entity, JsonNode.class);
		JsonNode jsonNode = respone.getBody();

		System.err.println(jsonNode.get("id"));

		String url2 = "http://localhost:8080/ede-customer/findbyusername/" + jsonNode.get("id");
		ResponseEntity<User> user = restTemplate.exchange(url2, HttpMethod.GET, entity, User.class);
		return user.getBody();
	}

	@Override
	public Shop getShopLogin(String header) {
		return shopdao.findByUser(this.getUserLogin(header));
	}

	@Override
	public void Security(String token, StringBuffer requestURL) throws HttpClientErrorException {
		System.err.println("security");
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		header.add("Authorization", "Bearer " + token);
		HttpEntity<Object> entity = new HttpEntity<Object>(null, header);
		RestTemplate restTemplate = new RestTemplate();
		String url = "";
		if (requestURL.toString().contains("admin")) {
			url = "http://localhost:8080/ede-oauth-service/api/test/admin/";
			ResponseEntity<JsonNode> respone = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);
		} else if (requestURL.toString().contains("user")) {
			url = "http://localhost:8080/ede-oauth-service/api/test/user/";
			ResponseEntity<JsonNode> respone = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);
		}
		System.err.println(requestURL + "url");

	}

}
