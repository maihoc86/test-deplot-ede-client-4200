package com.ede.edeproductservice.interceptor;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ede.edeproductservice.service.Auth_Service;

@SuppressWarnings("deprecation")
@Component
public class LogInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	Auth_Service authService;
	
    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler)
            throws Exception {
        long startTime = System.currentTimeMillis();
        System.out.println("\n-------- LogInterception.preHandle --- ");
        System.out.println("Request URL: " + request.getRequestURL());
        System.out.println("Start Time: " + System.currentTimeMillis());
 
        request.setAttribute("startTime", startTime);
    	  
        System.err.println(request.getHeader("Authorization"));
        try {
        	String token = request.getHeader("Authorization");
        	if(token.contains("Bearer")) {
        		token = token.substring(token.indexOf(" ")+1,token.length());
        		authService.getUserLogin(token);
        	}else {
        		authService.getUserLogin(token);
        	}
			 return true;
		} catch (Exception e) {
			System.err.println(e);
			return false;
		}
    }
    
}
