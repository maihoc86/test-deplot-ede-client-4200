package com.ede.edecustomerservice.restcontroller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edecustomerservice.entity.Cart;
import com.ede.edecustomerservice.entity.Cart_item;
import com.ede.edecustomerservice.entity.User;
import com.ede.edecustomerservice.service.Cart_Service;
import com.ede.edecustomerservice.service.Cart_item_Service;
import com.ede.edecustomerservice.service.Product_Option_Service;
import com.ede.edecustomerservice.service.User_Service;

@RestController
@RequestMapping("/ede-customer")
public class CartRestController {
	@Autowired
	Cart_Service service;
	@Autowired
	Cart_item_Service item_Service;
	@Autowired
	User_Service user_service;
	@Autowired
	Product_Option_Service product_Option_Service;
	public String generateUUID() {
		return UUID.randomUUID().toString();
	}
	@PutMapping("/updatecartitem/{iduser}")
	public ResponseEntity updateCart(@PathVariable("iduser")String id,@RequestBody List<Cart_item> listItem) {
		Cart cart = service.findByUserId(id);
		Cart cartNew = new Cart();
		if(cart==null) {
			cartNew.setId(generateUUID());
			cartNew.setUser(user_service.findById(id));
			service.save(cartNew);
		}else {
			item_Service.deleteByCartID(cart.getId());
		}
		for(int i = 0 ;i<=listItem.size()-1;i++) {
			listItem.get(i).setId(generateUUID());
			listItem.get(i).setCart(cart!=null?cart:cartNew);
			item_Service.save(listItem.get(i));
		}
		System.err.println(listItem);
		return ResponseEntity.ok(listItem);
	}
	@GetMapping("/getcartitem/{iduser}")
	public ResponseEntity getCartItem(@PathVariable("iduser")String id) {
		try {
			List<Object> listCart_items = service.getAllItemByUserID(id);
			return ResponseEntity.ok(listCart_items);	
		} catch (Exception e) {
			return null;
		}	
	}
}
