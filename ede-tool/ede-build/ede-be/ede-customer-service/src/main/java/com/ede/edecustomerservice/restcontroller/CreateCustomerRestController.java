package com.ede.edecustomerservice.restcontroller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ede.edecustomerservice.ResponseHandler;
import com.ede.edecustomerservice.dao.AuthoritiesDao;
import com.ede.edecustomerservice.dao.RoleDao;
import com.ede.edecustomerservice.entity.Authorities;
import com.ede.edecustomerservice.entity.Roles;
import com.ede.edecustomerservice.entity.Shop;
import com.ede.edecustomerservice.entity.User;
import com.ede.edecustomerservice.implement.mail.MailEntity;
import com.ede.edecustomerservice.service.CustomerService;
import com.ede.edecustomerservice.service.JsonWebTokenService;
import com.ede.edecustomerservice.service.MailService;
import com.ede.edecustomerservice.service.ShopService;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/ede-customer")
public class CreateCustomerRestController {
	@Autowired
	CustomerService service;

	@Autowired
	ShopService shopservice;

	@Autowired
	RoleDao roleDao;

	@Autowired
	AuthoritiesDao authoritiesDao;

	@Autowired
	HttpServletRequest request;

	@Autowired
	private JsonWebTokenService jwtService;

	@Autowired
	private MailService mailService;

	/**
	 * Register Account
	 * 
	 * @author hoc
	 * @see #register(User)
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping("/register")
	public ResponseEntity register(@RequestBody User user) {
		return checkDataUser(user);
	}

	/**
	 * Add New User by Admin
	 * 
	 * @author hoc
	 * @see #addNewUser(User)
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping("/admin/add-new-user")
	public ResponseEntity addNewUser(@RequestBody User user) {
		return checkDataUser(user);
	}

	// checkData user
	@SuppressWarnings("rawtypes")
	public ResponseEntity checkDataUser(@RequestBody User user) {
		UUID uuid = UUID.randomUUID();
		user.setId(uuid.toString());
		String validate = validateUser(user);
		if (validate != null && validate.equals("username")) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "T??i kho???n ???? t???n t???i", "username",
					null);
		} else if (validate != null && validate.equals("email")) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Email ???? t???n t???i", "email", null);
		} else if (validate != null && validate.equals("phone")) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "S??? ??i???n tho???i ???? t???n t???i", "phone",
					null);
		} else {
			Optional<Roles> roles = roleDao.findById("US");
			this.service.saveUser(user);
			Shop shop = new Shop();
			UUID sid = UUID.randomUUID();
			shop.setId(sid.toString());
			shop.setAddress(user.getAddress());
			shop.setCreate_date(new Date());
			shop.setImage("bia.jpg");
			shop.setName(user.getUsername());
			shop.setUser(service.findById(user.getId()).get());
			this.shopservice.save(shop);
			Authorities addAuthorities = new Authorities();
			addAuthorities.setUser(user);
			addAuthorities.setRole(roles.get());
			authoritiesDao.saveAndFlush(addAuthorities);
			return ResponseEntity.status(HttpStatus.OK).body(this.service.saveUser(user));
		}
	}

	// validate data user
	public String validateUser(@RequestBody User user) {
		User findByUsername = service.findByUsername(user.getUsername());
		User findByEmail = service.findByEmail(user.getEmail());
		User findByPhone = service.findByPhone(user.getPhone());
		if (findByUsername != null) {
			return "username";
		} else if (findByEmail != null) {
			return "email";
		} else if (findByPhone != null) {
			return "phone";
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/send-email-verify")
	public ResponseEntity activeAccountRegister(@RequestBody Map<String, String> request) {
		String email = request.get("email");
		System.out.println(email);
		Random rand = new Random();
		String otp = "";
		for (int i = 0; i < 6; i++) {
			otp += rand.nextInt(10);
		}
		// create and save token
		User userOri = this.service.findByEmailLike(email);
		System.out.println(userOri);
		if (null == userOri) {
			return ResponseEntity.ok(false);
		}
		String token = this.jwtService.createToken(otp, 1000 * 60 * 5); // 1000 * 60 * 5 = 5 minue
		System.err.println(token);
		userOri.setOtp(token);
		if (null == this.service.updateUserById(userOri)) {
			return ResponseEntity.ok(false);
		}
		// send mail
		MailEntity mail = new MailEntity();
		mail.setMailReceiver(email);
		mail.setSubject("K??ch ho???t t??i kho???n");
		mail.setText(String.format(
				"<a href=\"http://localhost:4200/account/verify?email=%s&token=%s\">Nh???p v??o ????y ????? x??c nh???n t??i kho???n</a> ",
				email, token));
		this.mailService.addMail(mail);
		return ResponseEntity.ok(true);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/account/verify")
	public ResponseEntity checkActiveAccount(@RequestParam String email, @RequestParam String token) {
		User active = service.findByEmail(email);
		if (active == null) {
			return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, true, "H??? th???ng kh??ng c?? Email n??y", "email",
					null);
		} else {
			if (active.getOtp() == null && active.getIs_active() == true) {
				return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, true, "T??i kho???n n??y ???? ???????c k??ch ho???t",
						"email", null);
			} else if (!active.getOtp().equals(token)) {
				return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Token kh??ng ch??nh x??c", "email",
						null);
			} else {
				if (jwtService.checkToken(token)) {
					active.setIs_active(true);
					active.setOtp(null);
					return ResponseEntity.status(HttpStatus.OK).body(this.service.saveUser(active));
				} else {
					active.setOtp(null);
					active.setIs_active(false);
					this.service.saveUser(active);
					return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Token ???? h???t h???n", "email",
							null);
				}
			}
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
	@PostMapping("/forgot-password/get-otp")
	ResponseEntity<Boolean> forgotPasswordGetOtp(@RequestBody Map<String, String> requestBody) {
		// create email
		String email = requestBody.get("email");
		Random rand = new Random();
		String otp = "";
		for (int i = 0; i < 6; i++) {
			otp += rand.nextInt(10);
		}
		// create and save token
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
		// send mail
		// FIXME s???a l???i tin nh???n s??? g???i ?????n ng?????i d??ng, ti??u ????? email, v?? ??a ng??n ng???
		MailEntity mail = new MailEntity();
		mail.setMailReceiver(email);
		mail.setSubject("Qu??n m???t kh???u");
		mail.setText(String.format(
				"M?? OTP l??: <b>%s</b> ho???c <a href=\"http://localhost:4200/forgot-password?email=%s&token=%s\">v??o ????y nhanh h??n</a> ",
				otp, email, token));
		this.mailService.addMail(mail);
		return ResponseEntity.ok(true);
	}

	/**
	 * Reset password with OTP
	 * 
	 * @author vinh
	 * @see #resetPasswordToken(User)
	 */
	@PatchMapping("/forgot-password/reset-password")
	ResponseEntity<Boolean> resetPasswordOtp(@RequestBody Map<String, String> requestBody) {
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
	@PatchMapping("/forgot-password/reset-password/token")
	ResponseEntity<Boolean> resetPasswordToken(@RequestBody Map<String, String> requestBody) {
		User user = new User();
		user.setEmail(requestBody.get("email"));
		user.setPassword(requestBody.get("password"));
		user.setOtp(requestBody.get("otp"));
		boolean b = this.service.resetPasswordToken(user);
		return ResponseEntity.ok(b);
	}


	

	
	/**
	 * Create search account admin
	 * @author Thanh
	 */
	

	
	@GetMapping("/getuserlogin/{token}")
	public User getUserLogin(@PathVariable("token") String toekn) {
		try {
			HttpHeaders header = new HttpHeaders();
			header.add("Content-Type", "application/json");
			header.add("Authorization", "Bearer " + toekn);

			RestTemplate restTemplate = new RestTemplate();
			String url = "http://localhost:8080/ede-oauth-service/api/auth/check/login";
			HttpEntity<Object> entity = new HttpEntity<Object>(null, header);
			ResponseEntity<JsonNode> respone = restTemplate.exchange(url, HttpMethod.POST, entity, JsonNode.class);
			JsonNode jsonNode = respone.getBody();

			System.err.println(jsonNode.get("id"));

			String url2 = "http://localhost:8080/ede-customer/findbyusername/" + jsonNode.get("id");
			ResponseEntity<User> user = restTemplate.exchange(url2, HttpMethod.GET, entity, User.class);
			return user.getBody();
		} catch (Exception e) {
			return null;
		}
	}

}
