package com.ede.edecustomerservice.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ede.edecustomerservice.dao.UserDao;
import com.ede.edecustomerservice.entity.User;
import com.ede.edecustomerservice.service.CustomerService;
import com.ede.edecustomerservice.service.JsonWebTokenService;

@Service
public class CustomerImpl implements CustomerService {
	
	@Autowired
	JsonWebTokenService jwtToken;
	
	@Autowired
	UserDao dao;

	@Override
	public User saveUser(User entity) {
		return dao.save(entity);
	}

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public boolean resetPasswordToken(User user) {
		if(!this.jwtToken.checkToken(user.getOtp())) {
			return false;
		}
		String otp = this.jwtToken.getValue(user.getOtp());
		user.setOtp(otp);
		return this.resetPasswordOtp(user);
	}

	@Override
	public boolean resetPasswordOtp(User user) {
		User userOri = this.dao.findByEmailLike(user.getEmail());
		if (!this.jwtToken.checkToken(userOri.getOtp())) {
			return false;
		}
		String otpOri = this.jwtToken.getValue(userOri.getOtp());
		if (!otpOri.equals(user.getOtp())) {
			return false;
		}
		userOri.setOtp(null);
		userOri.setPassword(user.getPassword());
		this.dao.save(userOri);
		return true;
	}

	@Override
	public User findByUsername(String username) {
		return dao.findByUsername(username);
	}

	@Override
	public User findByEmail(String username) {
		return dao.findByEmail(username);
	}

	@Override
	public User findByPhone(String phone) {
		return dao.findByPhone(phone);
	}
	
}
