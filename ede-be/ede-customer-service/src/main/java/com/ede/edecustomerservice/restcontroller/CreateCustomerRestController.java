package com.ede.edecustomerservice.restcontroller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.ede.edecustomerservice.ResponseHandler;
import com.ede.edecustomerservice.dao.AuthoritiesDao;
import com.ede.edecustomerservice.dao.RoleDao;
import com.ede.edecustomerservice.entity.Authorities;
import com.ede.edecustomerservice.entity.HistoryViewPage;
import com.ede.edecustomerservice.entity.Receive_news;
import com.ede.edecustomerservice.entity.Roles;
import com.ede.edecustomerservice.entity.Shop;
import com.ede.edecustomerservice.entity.User;
import com.ede.edecustomerservice.entity.UserAddress;
import com.ede.edecustomerservice.implement.mail.MailEntity;
import com.ede.edecustomerservice.service.Auth_Service;
import com.ede.edecustomerservice.service.CustomerService;
import com.ede.edecustomerservice.service.History_View_Page_Service;
import com.ede.edecustomerservice.service.JsonWebTokenService;
import com.ede.edecustomerservice.service.MailService;
import com.ede.edecustomerservice.service.Receive_news_Service;
import com.ede.edecustomerservice.service.ShopService;
import com.ede.edecustomerservice.service.UserAddress_Service;
import com.fasterxml.jackson.databind.JsonNode;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/ede-customer")
public class CreateCustomerRestController {
	@Autowired
	CustomerService service;

	@Autowired
	ShopService shopservice;

	@Autowired
	Auth_Service auth_service;

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

	@Autowired
	UserAddress_Service address_Service;

	@Autowired
	Receive_news_Service receive_news_Service;

	@Autowired
	History_View_Page_Service history_View_Page_Service;

	/**
	 * Register Account
	 * 
	 * @author hoc
	 * @see #register(User)
	 */
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
	@PostMapping("/admin/add-new-user")
	public ResponseEntity addNewUser(@RequestBody User user) {
		return checkDataUser(user);
	}

	public String generateUUID() {
		return UUID.randomUUID().toString();
	}

	// checkData user
	public ResponseEntity checkDataUser(@RequestBody User user) {
		user.setId(generateUUID());
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
			shop.setId(generateUUID());
			shop.setAddress(user.getAddress());
			shop.setCreate_date(new Date());
			shop.setImage("bia.jpg");
			shop.setName(user.getUsername());
			shop.setUser(service.findById(user.getId()).get());
			shop.setStatus(true);
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

	@PostMapping("/send-email-verify")
	public ResponseEntity activeAccountRegister(@RequestBody Map<String, String> request) {
		String email = request.get("email");
		Random rand = new Random();
		String otp = "";
		for (int i = 0; i < 6; i++) {
			otp += rand.nextInt(10);
		}
		// create and save token
		User userOri = this.service.findByEmailLike(email);
		;
		if (null == userOri) {
			return ResponseEntity.ok(false);
		}
		String token = this.jwtService.createToken(otp, 1000 * 60 * 5); // 1000 * 60 * 5 = 5 minue
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

	/**
	 * ????ng k?? nh???n email tin t???c t??? website
	 * 
	 * @param email
	 * @param token
	 * @return
	 */
	@PostMapping("/receive-email-news")
	public ResponseEntity receiveEmailNews(@RequestBody Map<String, String> request) {
		String email = request.get("email");
		Receive_news checkEmail = receive_news_Service.findEmail(email);
		if (checkEmail != null) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true,
					"Email n??y ???? ????ng k?? nh???n Email t??? website tr?????c ???? !", "email", null);
		} else {
			// send mail
			MailEntity mail = new MailEntity();
			mail.setMailReceiver(email);
			mail.setSubject("????ng k?? nh???n Email t??? Website EDE e-commerce");
			mail.setText(String.format(
					"<h1> C???m ??n b???n ???? ????ng k?? nh???n email t??? ch??ng t??i, nh???ng tin t???c m???i nh???t - gi???m gi?? - ch????ng tr??nh h???p d???n s??? ???????c c???p nh???t ?????n b???n s???m nh???t. </h1> </br>"
							+ "<p> <i>Tr??n tr???ng c???m ??n !</i> </p> "));
			this.mailService.addMail(mail);
			return ResponseEntity.ok(true);
		}

	}

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
					active.setIs_active(false);
					active.setOtp(null);
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
	 * 
	 * @author Vi???t
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
			System.out.print(e);
			return null;
		}
	}

	/**
	 * disable status shop (admin)
	 * 
	 * @author Kim Thanh
	 */

	@GetMapping("/updateStatus/shop/{id}")
	public Shop updateStatusShop(@PathVariable("id") String id, @RequestParam("status") Boolean status) {
		Shop shop = shopservice.findById(id).get();
		shop.setStatus(status);
		shopservice.save(shop);
		return shop;

	}

	/**
	 * @author thaihoc H??m th??m m???i ?????a ch???
	 * @param userAddress
	 * @param req
	 * @return ServerResponse
	 */
	@PostMapping("/user/add-new-address")
	public ResponseEntity addNewAddress(@RequestBody UserAddress userAddress, HttpServletRequest req) {

		User userLogin = new User();
		try {
			userLogin = auth_service.getUserLogin(req.getHeader("Authorization"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}

		if (userLogin.getId().equals(userAddress.getUser().getId())) {

			userAddress.setId(generateUUID());

			System.err.print(userAddress.getId());

			User findUser = service.findById(userAddress.getUser().getId()).get();

			if (findUser == null) {
				return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "User kh??ng t???n t???i", "user",
						null);
			}
			return ResponseEntity.ok(address_Service.saveAddress(userAddress));
		} else {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true,
					"B???n kh??ng ???????c th??m m???i ?????a ch??? l??n t??i kho???n ng?????i kh??c", "address", null);
		}
	}

	/**
	 * H??m g???i email li??n h??? cho ng?????i d??ng g???i t???i email Admin
	 * 
	 * @author Th??i H???c
	 * @throws IOException
	 */
	@PostMapping("/send-contact")
	public ResponseEntity sendContactEmail(@RequestPart("fullName") String fullName, @RequestPart("email") String email,
			@RequestPart("content") String content, @RequestPart("file") MultipartFile file) throws IOException {
		// send mail
		// FIXME s???a l???i tin nh???n s??? g???i ?????n ng?????i d??ng, ti??u ????? email, v?? ??a ng??n ng???
		MailEntity mail = new MailEntity();
		mail.setMailReceiver("teamsdarkeyes2021@gmail.com");
		mail.setSubject("G???i y??u c???u h??? tr???");
		mail.setText(content);
		mail.setText(String.format("<p>???? nh???n ???????c m???t khi???u n???i ho???c h??? tr??? t???: <b>%s</b></p></br>"
				+ "<p>C?? email l??: %s </p></br>" + "<p>V???i n???i dung: %s</p>", fullName, email, content));

		mail.setAttachment(convertMultiPartToFile(file));
		System.err.println(mail);
		this.mailService.addMail(mail);
		return ResponseEntity.ok(true); // TODO ch??a g???i ???????c email k??m file, do email ??ang l???i kh??ng g???i ???????c
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	@PostMapping("/add/viewPage")
	public ResponseEntity addViewPage(@RequestBody HistoryViewPage historyViewPage) {
		historyViewPage.setId(generateUUID());
		historyViewPage.setDate_view(new Date());
		return ResponseEntity.ok(history_View_Page_Service.addViewPage(historyViewPage));
	}

}