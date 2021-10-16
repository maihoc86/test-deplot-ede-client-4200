package com.ede.edeproductservice.restcontroller.user;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edeproductservice.ResponseHandler;
import com.ede.edeproductservice.entity.Evaluate;
import com.ede.edeproductservice.entity.Product_option;
import com.ede.edeproductservice.service.Auth_Service;
import com.ede.edeproductservice.service.Evaluate_service;
import com.ede.edeproductservice.service.Order_detail_service;
import com.ede.edeproductservice.service.Order_Service;
import com.ede.edeproductservice.service.ProductService;
import com.ede.edeproductservice.service.User_Service;


@RestController
@RequestMapping("/ede-product")
public class CreateCommentRestController {

	@Autowired
	Auth_Service authservice;

	@Autowired
	Order_Service order_service;
	
	@Autowired
	Order_detail_service order_detail_service;

	@Autowired
	ProductService product_service;
	
	@Autowired
	HttpServletRequest req;
	
	@Autowired
	Evaluate_service evaluate_service;
	
	@Autowired
	User_Service user_service;

	/**
	 * @author Kim Thanh Insert comment in product by idUser
	 * @throws IOException 
	 */
	
	
	public String generateUUID() {
		return UUID.randomUUID().toString();
	}
	

	
	@PostMapping("/insert/comment/user/{idUser}/{id}")
	public ResponseEntity  CreateCommentByIdUser( @PathVariable("idUser") String idUser, @PathVariable("id") String id, @RequestBody Evaluate comment,HttpServletResponse response ) throws IOException {
		List<Product_option> list = order_detail_service.findAllOptionProductByIduser(idUser,id);
		if( list.size() < 1) {
//			 response.sendError(403,"Bạn chưa mua sản phẩm nào của shop?");
//			 return null;
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Bạn chưa mua sản phẩm nào của shop!", null ,null);
		}
		List<Evaluate> evalue = evaluate_service.findByUserAndProduct(user_service.findById(idUser), product_service.findById(id));
		if( evalue.size() >= 1) {
			return ResponseHandler.generateResponse(HttpStatus.FORBIDDEN, true, "Bạn đã comment trước đó !", null ,null);
		}
		comment.setId(generateUUID());
		comment.setUser(user_service.findById(idUser));
		comment.setProduct(product_service.findById(id));
		 return ResponseEntity.ok( evaluate_service.save(comment));
	}
	
	@PutMapping("/update/comment/user/{idUser}/{id}")
	public ResponseEntity  UpdateCommentByIdUser( @PathVariable("idUser") String idUser, @PathVariable("id") String id, @RequestBody Evaluate comment,HttpServletResponse response ) {
		List<Evaluate> evalue = evaluate_service.findByUserAndProduct(user_service.findById(idUser), product_service.findById(id));
		Evaluate evaOld = evalue.get(0);
		evaOld.setContent(comment.getContent());
		evaOld.setRate(comment.getRate());
		 return ResponseEntity.ok( evaluate_service.save(evaOld));
	}
	
	
}
