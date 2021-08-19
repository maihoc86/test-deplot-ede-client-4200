package com.ede.edeoauth.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edeoauth.repository.UserRepository;
import com.ede.edeoauth.request.LoginRequest;
import com.ede.edeoauth.response.JwtResponse;
import com.ede.edeoauth.security.jwt.JwtUtils;
import com.ede.edeoauth.security.services.UserDetailServiceImpl;
import com.ede.edeoauth.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/ede-oauth-service/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserDetailServiceImpl userDetailServiceImpl;
	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		System.err.println("dsadsad");
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		System.err.println("authenticateUser");
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getUsername(), "Bearer ", 200, userDetails.getAuthorities()));
	}

	@RequestMapping("/check/login")
	public ResponseEntity<?> checkLogin(HttpServletRequest req) {
		System.err.println("Check login...");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		/// new token
		try {
			String jwt = createNewToken(req.getRemoteUser());
			return ResponseEntity.ok(new JwtResponse(jwt,req.getRemoteUser(), "Bearer ", 200, auth.getAuthorities()));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	
	}

	@RequestMapping("/check/admin")
	public ResponseEntity<?> checkADMIN(HttpServletRequest req) {
		System.err.println("Check admin...");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
			String jwt = createNewToken(req.getRemoteUser());

			return ResponseEntity.ok(new JwtResponse(jwt,req.getRemoteUser() ,"Bearer ", 200, auth.getAuthorities()));
		} else {
			return ResponseEntity.ok(new JwtResponse("", req.getRemoteUser(),"Bearer ", 403, auth.getAuthorities()));
		}

	}

	public String createNewToken(String username) {

		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				username, userRepository.findByUsername(username).get().getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		return jwt;
	}
}
