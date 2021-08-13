package com.ede.edecustomerservice.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ede.edecustomerservice.dao.UserDao;
import com.ede.edecustomerservice.entity.User;
import com.ede.edecustomerservice.service.CustomerService;
import com.ede.edecustomerservice.service.JsonWebTokenService;

@Service
@Transactional
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
		return dao.findAll();
	}

	@Override
	public boolean resetPasswordToken(User user) {
		if (!this.jwtToken.checkToken(user.getOtp())) {
			return false;
		}
		String otp = this.jwtToken.getValue(user.getOtp());
		user.setOtp(otp);
		return this.resetPasswordOtp(user);
	}

	@Override
	public boolean resetPasswordOtp(User user) {
		User userOri = this.dao.findByEmailLike(user.getEmail());
		if (null == userOri) {
			return false;
		}
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
	public User findByEmailLike(String email) {
		return this.dao.findByEmailLike(email);
	}

	@Override
	public User updateUserById(User userUpdate) {
		User userOri = this.dao.findById(userUpdate.getId()).orElse(null);
		if (null == userOri) {
			return null;
		}
		return this.dao.save(userUpdate);
	}

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

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public User deleteByUsername(String username) {
		User u = dao.findByUsername(username);
		u.setIs_delete(true);
		dao.save(u);
		return u;
	}

	@Override
	public boolean existsById(String id) {
		return this.dao.existsById(id);
	}

	@Override
	public boolean existsUsername(String username) {
		return null != this.dao.findByUsername(username);
	}

	@Override
	public User findById(String id) {
		return dao.findById(id).get();
	}

}
