//package com.ede.edeproductservice;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.env.ConfigurableEnvironment;
//import org.springframework.core.env.Environment;
//import org.springframework.core.env.MapPropertySource;
//import org.springframework.core.env.MutablePropertySources;
//import org.springframework.stereotype.Service;
//import org.springframework.web.context.annotation.RequestScope;
//
//@Service
//@RequestScope
//public class GetValuCloud {
//	
//	@Value("${spring.mail.host}")
//	private String hostHostMail;
//	@Value("${spring.mail.port}")
//	private String portMail;
//	@Value("${spring.mail.username}")
//	private String usernameMail;
//	@Value("${spring.mail.password}")
//	private String passwordMail;
////	@Value("${spring.mail.properties.mail.smtp.auth}")
////	private Boolean auth;
////	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
////	private Boolean enable;
//	@Autowired
//	private ConfigurableEnvironment environment;
//
//	@Autowired
//	private Environment env;
//	
//	public MutablePropertySources getConfigValueMap() {
//		MutablePropertySources propertySources = environment.getPropertySources();
//		Map<String, Object> mapMail = new HashMap<>();
//		mapMail.put("host", hostHostMail);
//		mapMail.put("port", portMail);
//		mapMail.put("username", usernameMail);
//		mapMail.put("password", passwordMail);
////		mapMail.put("properties.mail.smpt.auth", auth);
////		mapMail.put("properties.mail.smpt.starttls.enable", enable);
//		System.out.println(mapMail);
//		propertySources.addFirst(new MapPropertySource("spring.mail", mapMail));
//		System.out.println(propertySources);
//		return propertySources;
//	}
//	public String getConfigValue(String key) {
//		return env.getProperty(key);
//	}
//}
