package com.ede.edeoauth.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.ede.edeoauth.entity.User;
import com.ede.edeoauth.repository.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username " + username));
		System.err.println(user);
		return UserDetailsImpl.build(user);

//		 if ("admin".equals(username)) {
//	            return org.springframework.security.core.userdetails.User.withUsername("admin").password("admin").roles("USER").build();
//	        } else {
//	            throw new UsernameNotFoundException(username);
//	        }
	}

}
