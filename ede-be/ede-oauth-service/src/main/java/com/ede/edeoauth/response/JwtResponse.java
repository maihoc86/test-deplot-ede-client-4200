package com.ede.edeoauth.response;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.ede.edeoauth.entity.Authority;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private int status;
	private Collection<? extends GrantedAuthority> role;
	
}
