package com.ede.edeproductservice.restcontroller.user;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edeproductservice.ResponseHandler;
import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.entity.Product_discount;
import com.ede.edeproductservice.entity.Product_option;
import com.ede.edeproductservice.entity.Product_option_image;
import com.ede.edeproductservice.entity.Product_tag;
import com.ede.edeproductservice.entity.Shop;
import com.ede.edeproductservice.entity.User;
import com.ede.edeproductservice.service.Auth_Service;
import com.ede.edeproductservice.service.ProductService;
import com.ede.edeproductservice.service.Product_Tag_service;
import com.ede.edeproductservice.service.Product_brand_service;
import com.ede.edeproductservice.service.Product_child_category_service;
import com.ede.edeproductservice.service.Product_discount_service;
import com.ede.edeproductservice.service.Product_option_image_service;
import com.ede.edeproductservice.service.Product_option_service;
import com.ede.edeproductservice.service.ShopService;

@RestController
@RequestMapping("/ede-product")
public class UpdateProductRestController {
	@Autowired
	ProductService service;

	@Autowired
	Auth_Service auth_service;

	@Autowired
	ShopService shopService;

	@Autowired
	Product_brand_service brandService;

	@Autowired
	Product_child_category_service category_service;

	@Autowired
	Product_option_service product_option_service;

	@Autowired
	Product_option_image_service product_option_image_service;

	@Autowired
	Product_discount_service product_discount_service;

	@Autowired
	Product_Tag_service product_Tag_service;

	public String generateUUID() {
		return UUID.randomUUID().toString();
	}

	@SuppressWarnings("rawtypes")
	@PutMapping("/user/update/product-shop/")
	public ResponseEntity updateProduct(@RequestBody Product product, HttpServletRequest req) {
		User us = new User();
		try {
			us = auth_service.getUserLogin(req.getHeader("Authorization"));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		Shop sh = shopService.findByUser(us);
		product.setShop(sh);
		return ResponseEntity.status(HttpStatus.OK).body(service.save(product));
	}

	@SuppressWarnings("rawtypes")
	@PutMapping("/user/update/product-shop/discount")
	public ResponseEntity updateProductDiscount(@RequestBody Product_discount discount) {
		return ResponseEntity.status(HttpStatus.OK).body(product_discount_service.save(discount));
	}

	@SuppressWarnings("rawtypes")
	@PutMapping("/user/update/product-shop/options/")
	public ResponseEntity updateProductOptions(@RequestBody Product_option product_option) {
		System.err.println(product_option);
		return ResponseEntity.status(HttpStatus.OK).body(product_option_service.save(product_option));
	}

	@SuppressWarnings("rawtypes")
	@PutMapping("/user/update/product-shop/options/images")
	public ResponseEntity updateProductOptionImage(@RequestBody Product_option_image product_option) {
		if (product_option != null) {
			product_option_image_service.deleteAllImage(product_option.getProductoption().getId());
			String[] words = product_option.getImage().split(",");
			for (int i = 0; i < words.length; i++) {
				product_option.setId(generateUUID().toString());
				product_option.setImage(words[i]);
				product_option_image_service.save(product_option);
			}
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "Cập nhật hình ảnh thành công", "", null);
	}

	// TODO: java.lang.NullPointerException: Cannot invoke
	// "String.contains(java.lang.CharSequence)" because "token" is null
	@SuppressWarnings("rawtypes")
	@PutMapping("/user/enable/product-shop/")
	public ResponseEntity enableProductAndSell(@RequestBody Product product) {
		System.err.println(product);
		product.setEnable(true);
		return ResponseEntity.status(HttpStatus.OK).body(service.save(product));
	}

	@SuppressWarnings("rawtypes")
	@PutMapping("/user/update/product-shop/tag")
	public ResponseEntity updateProductTag(@RequestBody Product_tag product_tag) {
		if (product_tag != null) {
			product_Tag_service.deleteAllTagByIdProduct(product_tag.getProducttag().getId());
			String[] words = product_tag.getTag().split(",");
			for (int i = 0; i < words.length; i++) {
				product_tag.setId(generateUUID().toString());
				product_tag.setTag(words[i]);
				product_Tag_service.save(product_tag);
			}
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "Cập nhật tag thành công", "", null);
	}

}