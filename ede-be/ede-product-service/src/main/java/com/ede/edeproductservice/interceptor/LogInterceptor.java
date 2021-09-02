package com.ede.edeproductservice.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import io.sentry.Sentry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ede.edeproductservice.entity.User;
import com.ede.edeproductservice.service.Auth_Service;
@Component
public class LogInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	Auth_Service authService;
    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler)
    throws Exception,HttpClientErrorException	{
        long startTime = System.currentTimeMillis();
        System.out.println("\n-------- LogInterception.preHandle --- ");
        System.out.println("Request URL: " + request.getRequestURL());
        System.out.println("Start Time: " + System.currentTimeMillis());
 
        request.setAttribute("startTime", startTime);
    	  
        System.err.println(request.getHeader("Authorization"));
        String token = request.getHeader("Authorization");

          try {
        	  authService.Security(token,request.getRequestURL());
		} catch (HttpClientErrorException e) {
			System.err.println(e.getRawStatusCode());
			 response.sendError(e.getRawStatusCode(),e.getStatusCode().toString());
			 return false;
		}
        return true;
        
    }
}
