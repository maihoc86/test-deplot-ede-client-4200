package com.ede.edeorderservice;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class ResponseHandler {

	public static ResponseEntity<Object> generateResponse(HttpStatus status, boolean error, String message,String field,
			Object responseObj) {
		Map<String, Object> map = new HashMap<String, Object>();
		 JSONArray array = new JSONArray();
		 JSONObject object = new JSONObject();
		 object.put("defaultMessage", message);
		 object.put("field", field);
		 array.add(object);
		try {
			map.put("timestamp", new Date());
			map.put("status", status.value());
			map.put("isSuccess", error);
			map.put("message", message);
			map.put("data", responseObj);
			map.put("errors", array);
			return new ResponseEntity<Object>(map, status);
		} catch (Exception e) {
			map.clear();
			map.put("timestamp", new Date());
			map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("isSuccess", false);
			map.put("message", e.getMessage());
			map.put("data", null);
			return new ResponseEntity<Object>(map, status);
		}
	}
}