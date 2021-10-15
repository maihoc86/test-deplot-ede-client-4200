package com.ede.edeoauth.controller;
import javax.servlet.http.HttpServletRequest;

import org.bouncycastle.cert.ocsp.Req;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edeoauth.response.JwtResponse;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/ede-oauth-service/api/test")
public class TestAPI {
	@GetMapping("/all")
	public String allAccess(HttpServletRequest req) {
		
		return "Public Content."+req.getUserPrincipal();
	}
	
	@GetMapping("/user/")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> userAccess(HttpServletRequest req) {
		String token = req.getHeader("Authorization");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return ResponseEntity.ok(new JwtResponse(token, req.getRemoteUser(), "Bearer ",200 , auth.getAuthorities()));
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/admin/")
	@PreAuthorize("hasRole('ADMIN')")
	public  ResponseEntity<?> adminAccess(HttpServletRequest req) {
		String token = req.getHeader("Authorization");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return ResponseEntity.ok(new JwtResponse(token, req.getRemoteUser(), "Bearer ",200 , auth.getAuthorities()));
	}
}
