package com.ede.edeproductservice.interceptor;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	@Autowired
	LogInterceptor login;
	  @Override
	   public void addInterceptors(InterceptorRegistry registry) {
	      // LogInterceptor apply to all URLs.
	      registry.addInterceptor(login)
	      .excludePathPatterns(
	    		  "/ede-product/view/get-products",
	    		  "/ede-product/view/getAllProduct"
	    		  );
	   }
}
