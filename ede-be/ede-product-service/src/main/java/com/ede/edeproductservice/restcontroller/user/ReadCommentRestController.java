package com.ede.edeproductservice.restcontroller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edeproductservice.entity.Evaluate;
import com.ede.edeproductservice.entity.Product_option;
import com.ede.edeproductservice.service.Auth_Service;
import com.ede.edeproductservice.service.Order_Detail_service;
import com.ede.edeproductservice.service.Order_Service;
import com.ede.edeproductservice.service.ProductService;

@RestController
@RequestMapping("/ede-product")
public class ReadCommentRestController {



	@Autowired
	Auth_Service authservice;

	@Autowired
	Order_Service order_service;
	
	@Autowired
	Order_Detail_service order_detail_service;

	@Autowired
	ProductService product_service;
	
	@Autowired
	HttpServletRequest req;

	/**
	 * @author Kim Thanh Get comment in product by id
	 */
	@GetMapping("/view/comment/{id}")
	public List<Evaluate> getCommentById(@PathVariable("id") String id) {
		List<Evaluate>list = product_service.findAllCommnentByIdProduct(id);
		return list;
	}
	
	/**
	 * @author Kim Thanh Get comment in product by id  => find option
	 */
	
	@GetMapping("/view/comment/optionProduct/{idUser}/{id}")
	public List<Product_option> getAllOptionProductByUser(@PathVariable("idUser") String idUser, @PathVariable("id") String id){
		return  order_detail_service.findAllOptionProductByIduser(idUser, id);
	}
	
	

	
	
	
}
