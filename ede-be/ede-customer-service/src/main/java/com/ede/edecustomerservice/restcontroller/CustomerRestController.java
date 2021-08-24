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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

@CrossOrigin("*")
@RestController
public class CustomerRestController {

	@Autowired
	CustomerService service;
	
	@Autowired
	ShopService shopservice;

	@Autowired
	private MailService mailService;

	@Autowired
	RoleDao roleDao;

	@Autowired
	AuthoritiesDao authoritiesDao;

	@Autowired
	private JsonWebTokenService jwtService;

	@Autowired
	HttpServletRequest request;

	/**
	 * Register Account
	 * 
	 * @author hoc
	 * @see #register(User)
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping("/ede-customer/register")
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
	@PostMapping("/ede-customer/admin/add-new-user")
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
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Tài khoản đã tồn tại", "username",
					null);
		} else if (validate != null && validate.equals("email")) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Email đã tồn tại", "email", null);
		} else if (validate != null && validate.equals("phone")) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Số điện thoại đã tồn tại", "phone",
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
			shop.setUser(service.findById(user.getId()));
			this.shopservice.save(shop);
			Authorities addAuthorities = new Authorities();
			addAuthorities.setUser(user);
			addAuthorities.setRole(roles.get());
			authoritiesDao.saveAndFlush(addAuthorities);
			return ResponseEntity.status(HttpStatus.OK).body(this.service.saveUser(user));
		}
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/ede-customer/send-email-verify")
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
		mail.setSubject("Kích hoạt tài khoản");
		mail.setText(String.format(
				"<a href=\"http://localhost:4200/account/verify?email=%s&token=%s\">Nhấp vào đây để xác nhận tài khoản</a> ",
				email, token));
		this.mailService.addMail(mail);
		return ResponseEntity.ok(true);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/ede-customer/account/verify")
	public ResponseEntity checkActiveAccount(@RequestParam String email, @RequestParam String token) {
		User active = service.findByEmail(email);
		if (active == null) {
			return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, true, "Hệ thống không có Email này", "email",
					null);
		} else {
			if (active.getOtp() == null && active.getIs_active() == true) {
				return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, true, "Tài khoản này đã được kích hoạt",
						"email", null);
			} else if (!active.getOtp().equals(token)) {
				return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Token không chính xác", "email",
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
					return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Token đã hết hạn", "email",
							null);
				}
			}
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
		// FIXME sửa lại tin nhắn sẽ gửi đến người dùng, tiêu đề email, và đa ngôn ngữ
		MailEntity mail = new MailEntity();
		mail.setMailReceiver(email);
		mail.setSubject("Quên mật khẩu");
		mail.setText(String.format(
				"Mã OTP là: <b>%s</b> hoặc <a href=\"http://localhost:4200/forgot-password?email=%s&token=%s\">vào đây nhanh hơn</a> ",
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
	@PatchMapping("/ede-customer/forgot-password/reset-password")
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
	@PatchMapping("/ede-customer/forgot-password/reset-password/token")
	ResponseEntity<Boolean> resetPasswordToken(@RequestBody Map<String, String> requestBody) {
		User user = new User();
		user.setEmail(requestBody.get("email"));
		user.setPassword(requestBody.get("password"));
		user.setOtp(requestBody.get("otp"));
		boolean b = this.service.resetPasswordToken(user);
		return ResponseEntity.ok(b);
	}

	@DeleteMapping("/ede-customer/delete/users/{username}")
	public ResponseEntity<User> deleteUserByUsername(@PathVariable("username") String username) {
		System.err.println("Detele username :" + username);
		try {
			return ResponseEntity.ok(service.deleteByUsername(username));
		} catch (Exception e) {
			System.err.println(e);
			return ResponseEntity.notFound().build();
		}
	}
	
	/**
	 * Load data on the table
	 * 
	 * @author thanh
	 * @see
	 */
	@GetMapping("/ede-customer/admin/users")
	public List<User> getAccounts() {
		return service.findAll();
	}
	
	/**
	 * Create search account admin
	 * @author Thanh
	 */
	@GetMapping("/ede-customer/admin/search/{username}")
	public List<User> search(@PathVariable("username") String username){
		System.out.println("username: "+username);
		if(username == "") {
			System.out.println("ahihi lala ");
			return this.getAccounts();
			
		}
		else {
			return service.findByUsernameContaining(username);
		}
		
	}
	@GetMapping("/ede-customer/findbyusername/{username}")
	public User findbyusername(@PathVariable("username") String username) {
		String u =	username.substring(1, username.length()-1);
		System.err.println("in api :" +service.findByUsername(u)+" id la " +username);
		return service.findByUsername(u);
	}
	
	@GetMapping("/ede-customer/getuserlogin/{token}")
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
