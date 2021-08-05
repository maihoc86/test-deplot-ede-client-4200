package com.ede.edecustomerservice.restcontroller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edecustomerservice.entity.User;
import com.ede.edecustomerservice.implement.mail.MailEntity;
import com.ede.edecustomerservice.service.CustomerService;
import com.ede.edecustomerservice.service.MailService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CustomerRestController {

	@Autowired
	CustomerService service;
	
	@Autowired
	private MailService mailService;

	@RequestMapping("/ede-customer/index")
	public String getView() {
		return "Hello World";
	}

	@PostMapping("/ede-customer/register")
	public ResponseEntity<User> register(@RequestBody User user) {
		System.out.println(user);
		UUID uuid = UUID.randomUUID();
		System.out.println(uuid);
		user.setId(uuid.toString());
		System.out.println(user);
		System.out.println("Vào đây r");
		return ResponseEntity.status(HttpStatus.OK).body(this.service.saveUser(user));
	}
	
	/**
	 * Use to send otp to email for user contain url with token and otp
	 * @author vinh
	 * @param email is address of otp receiver
	 * @return Mail sended sucess
	 */
	@PostMapping("/ede-customer/forget-password")
	public ResponseEntity<Boolean> forgetPasswordGetOtp(@RequestBody(required = true) String email) {
		MailEntity mail = new MailEntity();
		mail.setSubject("");
		this.mailService.addMail(mail);
		return ResponseEntity.ok(true);
	}
	
	@GetMapping("/rest/test")
	public String tt() {
		MailEntity mail = new MailEntity();
		mail.setMailReceiver("mvinhle22@gmail.com");
		mail.setSubject("subject");
		mail.setText("Nội dung <b>Nè</b>è");
		this.mailService.addMail(mail);
		return "Ok";
	}
	
}
