package com.ede.edecustomerservice.implement;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.ede.edecustomerservice.service.JsonWebTokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Create, Read, Validation token with encode HS256
 * @author vinh
 */
@Service
public class JsonWebTokenServiceAdapter implements JsonWebTokenService {
	
	// XXX Có nên sửa thành UUID ?
	private final String JWT_SECRET = "Sign_Of_Token-NeUw2Nyc2dN%M?Kzj8jnaDh*q_W%9L";
	
	@Override
	public boolean checkToken(String token) {
		try {
			Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	@Override
	public String createToken(String subject, long lifeTime) {
		Date now = new Date();
		return Jwts.builder()
				.signWith(SignatureAlgorithm.HS256, JWT_SECRET)
				.setSubject(subject)
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + lifeTime))
				.compact();
	}

	@Override
	public String getValue(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(JWT_SECRET)
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}

}
