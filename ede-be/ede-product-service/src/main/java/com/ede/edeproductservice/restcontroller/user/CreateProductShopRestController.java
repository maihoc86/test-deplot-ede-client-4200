package com.ede.edeproductservice.restcontroller.user;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

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
import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.entity.Product_discount;
import com.ede.edeproductservice.entity.Product_meta;
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
import com.ede.edeproductservice.service.Product_meta_service;
import com.ede.edeproductservice.service.Product_option_image_service;
import com.ede.edeproductservice.service.Product_option_service;
import com.ede.edeproductservice.service.ShopService;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/ede-product")
public class CreateProductShopRestController {
	@Autowired
	Auth_Service auth_service;

	@Autowired
	ProductService service;

	@Autowired
	Product_brand_service brandService;

	@Autowired
	Product_child_category_service category_service;

	@Autowired
	Product_option_service product_option_service;

	@Autowired
	Product_option_image_service product_option_image_service;

	@Autowired
	Product_Tag_service product_Tag_service;

	@Autowired
	Product_discount_service product_discount_service;

	@Autowired
	ShopService shopService;

	@Autowired
	Product_meta_service product_meta_service;

	public String generateUUID() {
		return UUID.randomUUID().toString();
	}

	@PostMapping("/user/create/product-shop")
	public ResponseEntity addProductAndSell(@RequestBody Product product, HttpServletRequest req) {
		User us = new User();
		try {
			us = auth_service.getUserLogin(req.getHeader("Authorization"));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}

		product.setId(generateUUID().toString());
		product.setEnable(false);
		product.setCreatedate(new Date());
		/************************/

		Shop sh = shopService.findByUser(us);
		product.setShop(sh);

		return ResponseEntity.status(HttpStatus.OK).body(service.save(product));
	}

	@PostMapping("/user/create/product-shop/options/")
	public ResponseEntity addProductOptions(@RequestBody Product_option product_option, HttpServletRequest req) {

		if (product_option_service.countItemByProductID(product_option.getProduct().getId()) == 10) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true,
					"Bạn đã có 10 thuộc tính sản phẩm, vui lòng xóa bớt !", "", null);
		} else {
			product_option.setId(generateUUID().toString());

			return ResponseEntity.status(HttpStatus.OK).body(product_option_service.save(product_option));
		}
	}

	@PostMapping("/user/create/product-shop/options/images")
	public ResponseEntity addProductOptionImage(@RequestBody Product_option_image product_option_image) {
		String[] words = product_option_image.getImage().split(",");
		for (int i = 0; i < words.length; i++) {
			Optional<Product_option_image> findImage = product_option_image_service.findById(generateUUID().toString());
			if (findImage.isPresent() && findImage != null) {
				product_option_image.setId(generateUUID().toString());
				product_option_image.setImage(words[i]);
				product_option_image_service.save(product_option_image);
			} else {
				product_option_image.setId(generateUUID().toString());
				product_option_image.setImage(words[i]);
				product_option_image_service.save(product_option_image);
			}
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "Thêm hình ảnh thành công", "", null);
	}

	@PostMapping("/user/create/product-shop/discount")
	public ResponseEntity addProductDiscount(@RequestBody Product_discount product_discount) {
		try {
			product_discount.setId(generateUUID().toString());
			product_discount.setStatus(true);
			// TODO: Sửa product discount
			return ResponseEntity.status(HttpStatus.OK).body(product_discount_service.save(product_discount));
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true,
					"Trong thời gian này, sản phẩm đã được giảm giá !", "startdate", null);
		}

	}

	@PostMapping("/user/create/product-shop/tag")
	public ResponseEntity addProductTag(@RequestBody Product_tag product_tag) {

		String[] words = product_tag.getTag().split(",");
		for (int i = 0; i < words.length; i++) {
			Optional<Product_tag> findTag = product_Tag_service.findById(generateUUID().toString());
			if (findTag.isPresent() && findTag != null) {
				product_tag.setId(generateUUID().toString());
				product_tag.setTag(words[i]);
				product_Tag_service.save(product_tag);
			} else {
				product_tag.setId(generateUUID().toString());
				product_tag.setTag(words[i]);
				product_Tag_service.save(product_tag);
			}
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "Thêm tag thành công", "", null);

	}

	@PutMapping("/user/enable/product-shop/{id}")
	public ResponseEntity enableProductAndSell(@PathVariable("id") String id) {
		Product product = service.findById(id);
		product.setEnable(true);
		return ResponseEntity.status(HttpStatus.OK).body(service.save(product));
	}

	@PostMapping("/view/product-meta/add")
	public ResponseEntity addProductMeta(@RequestBody Product_meta product_meta) {
		product_meta.setId(generateUUID().toString());
		return ResponseEntity.status(HttpStatus.OK).body(product_meta_service.save(product_meta));
	}

}
