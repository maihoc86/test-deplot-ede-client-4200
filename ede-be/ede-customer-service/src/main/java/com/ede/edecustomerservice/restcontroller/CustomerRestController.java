package com.ede.edecustomerservice.restcontroller;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edecustomerservice.ResponseHandler;
import com.ede.edecustomerservice.entity.User;
import com.ede.edecustomerservice.implement.mail.MailEntity;
import com.ede.edecustomerservice.service.CustomerService;
import com.ede.edecustomerservice.service.JsonWebTokenService;
import com.ede.edecustomerservice.service.MailService;

@CrossOrigin("*")
@RestController
public class CustomerRestController {

	@Autowired
	CustomerService service;

	@Autowired
	private MailService mailService;

	@Autowired
	private JsonWebTokenService jwtService;

	@PostMapping("/ede-customer/register")
	public ResponseEntity register(@RequestBody User user) {
		UUID uuid = UUID.randomUUID();
		user.setId(uuid.toString());

		User findByEmail = service.findByEmail(user.getEmail());
		User findByUsername = service.findByUsername(user.getUsername());
		User findByPhone = service.findByPhone(user.getPhone());
		if (findByUsername != null) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Tài khoản đã tồn tại", "username",
					null);
		} else if (findByEmail != null) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Email đã tồn tại", "email", null);
		} else if (findByPhone != null) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Số điện thoại đã tồn tại", "phone",
					null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(this.service.saveUser(user));
		}

	}

	/**
	 * Use for user forgot Password <br/>
	 * Use to send otp to email for user contain url with token and otp
	 * 
	 * @author vinh
	 * @param email is address of otp receiver
	 * @return True if mail added into schedule
	 */
	@PostMapping("/ede-customer/forgot-password/get-otp")
	ResponseEntity<Boolean> forgotPasswordGetOtp(@RequestBody Map<String, String> requestBody) {
		//create email
		String email = requestBody.get("email");
		Random rand = new Random();
		String otp = "";
		for (int i = 0; i < 6; i++) {
			otp += rand.nextInt(10);
		}
		//create and save token
		User userOri = this.service.findByEmailLike(email);
		if (null == userOri) {
			return ResponseEntity.ok(false);
		}
		String token = this.jwtService.createToken(otp, 1000 * 60 * 5); // 1000 * 60 * 5 = 5 minue
		System.err.println(token);
		userOri.setOtp(token);
		if (null == this.service.updateUserById(userOri)) {
			return ResponseEntity.ok(false);
		}
		//send mail
		MailEntity mail = new MailEntity();
		mail.setMailReceiver(email);
		mail.setSubject("Quên mật khẩu");
		mail.setText(String.format("Mã OTP là: <b>%s</b> hoặc <a href=\"http://localhost:4200/forgot-password?email=%s&token=%s\">vào đây nhanh hơn</a> ", otp, email, token));
		this.mailService.addMail(mail);
		return ResponseEntity.ok(true);
	}

	/**
	 * Reset password with OTP
	 * 
	 * @author vinh
	 * @see #resetPasswordToken(User)
	 */
	@PostMapping("/ede-customer/forgot-password/reset-password")
	ResponseEntity<Boolean> resetPasswordOtp(@RequestBody Map<String, String> requestBody){
		User user = new User();
		user.setEmail(requestBody.get("email"));
		user.setPassword(requestBody.get("password"));
		user.setOtp(requestBody.get("otp"));
		boolean b = this.service.resetPasswordOtp(user);
		return ResponseEntity.ok(b);
	}

	/**
	 * Reset password with Token
	 * 
	 * @author vinh
	 * @see #resetPasswordOtp(User)
	 * @see #resetPasswordToken(User)
	 */
	@PostMapping("/ede-customer/forgot-password/reset-password/token")
	ResponseEntity<Boolean> resetPasswordToken(@RequestBody Map<String, String> requestBody){
		User user = new User();
		user.setEmail(requestBody.get("email"));
		user.setPassword(requestBody.get("password"));
		user.setOtp(requestBody.get("otp"));
		boolean b = this.service.resetPasswordToken(user);
		return ResponseEntity.ok(b);
	}

}
