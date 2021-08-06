package com.ede.edecustomerservice.restcontroller;

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
	 * Use for user Forget Password <br/>
	 * Use to send otp to email for user contain url with token and otp
	 * 
	 * @author vinh
	 * @param email is address of otp receiver
	 * @return True if mail added into schedule
	 */
	@PostMapping("/ede-customer/forget-password/get-otp")
	ResponseEntity<Boolean> forgetPasswordGetOtp(@RequestBody(required = true) String email) {
		Random rand = new Random();
		String otp = "";
		for (int i = 0; i < 6; i++) {
			otp += rand.nextInt(10);
		}
		String token = this.jwtService.createToken(otp, 1000 * 60 * 5); // 1000 * 60 * 5 = 5 minue

		MailEntity mail = new MailEntity();
		mail.setMailReceiver(email);
		mail.setSubject("Quên mật khẩu");
		mail.setText(String.format(
				"Mã OTP là: <b>%s</b> hoặc <a href=\"http://localhost:4200/forget-password?token=$s\">vào đây nhanh hơn</a> ",
				otp, token));
		this.mailService.addMail(mail);
		return ResponseEntity.ok(true);
	}

	/**
	 * Reset password with OTP
	 * 
	 * @author vinh
	 * @see #resetPasswordToken(User)
	 */
	@PostMapping("/ede-customer/forget-password/reset-password/")
	ResponseEntity<Boolean> resetPasswordOtp(@RequestBody User user) {
		return ResponseEntity.ok(this.service.resetPasswordOtp(user));
	}

	/**
	 * Reset password with Token
	 * 
	 * @author vinh
	 * @see #resetPasswordOtp(User)
	 * @see #resetPasswordToken(User)
	 */
	@PostMapping("/ede-customer/forget-password/reset-password/token")
	ResponseEntity<Boolean> resetPasswordToken(@RequestBody User user) {
		return ResponseEntity.ok(this.service.resetPasswordToken(user));
	}

}
