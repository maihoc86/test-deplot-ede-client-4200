package com.ede.edeoauth.response;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
	private String token;
	private String id;
	private String type = "Bearer";
	private int status;
	private Collection<? extends GrantedAuthority> role;
	
}
