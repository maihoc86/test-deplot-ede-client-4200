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
import com.ede.edeproductservice.entity.Orderdetail;
import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.entity.Product_discount;
import com.ede.edeproductservice.entity.Product_option;
import com.ede.edeproductservice.entity.Product_option_image;
import com.ede.edeproductservice.entity.Product_tag;
import com.ede.edeproductservice.entity.Shop;
import com.ede.edeproductservice.entity.User;
import com.ede.edeproductservice.service.Auth_Service;
import com.ede.edeproductservice.service.Order_detail_service;
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

	@Autowired
	Order_detail_service order_detail_service;

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
		// TODO s???a l???i discount tr??n option
		// TODO t??ch h??m
		Orderdetail findProductOption = order_detail_service.findProductOption(discount.getProductdiscount().getId());
		if (findProductOption != null) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true,
					"S???n ph???m n??y ???? ???????c b??n, kh??ng ???????c s???a gi???m gi?? !", "id", null);
		} else {

			return ResponseEntity.status(HttpStatus.OK).body(product_discount_service.save(discount));
		}
	}

	/**
	 * H??m c???p nh???t thu???c t??nh s???n ph???m
	 * 
	 * @param product_option Thu???c t??nh s???n ph???m
	 * @param req            AUTH
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PutMapping("/user/update/product-shop/options/")
	public ResponseEntity updateProductOptions(@RequestBody Product_option product_option, HttpServletRequest req) {

		User us = new User();
		try {
			us = auth_service.getUserLogin(req.getHeader("Authorization"));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		Shop sh = shopService.findByUser(us);

		Product_option findProductOption = product_option_service.findById(product_option.getId());

		if (findProductOption != null) {
			if (sh.getId() != findProductOption.getProduct().getShop().getId()) {
				return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true,
						"B???n kh??ng ???????c ch???nh s???a s???n ph???m c???a h??ng kh??c !", "id", null);
			} else {
				Orderdetail findProductOptionOrder = order_detail_service.findProductOption(product_option.getId());
				if (findProductOptionOrder != null && product_option.getPrice() != findProductOption.getPrice()) {
					return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true,
							"S???n ph???m n??y ???? ???????c b??n, kh??ng ???????c s???a gi?? !", "id", null);
				} else {
					return ResponseEntity.status(HttpStatus.OK).body(product_option_service.save(product_option));
				}
			}
		} else {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Kh??ng t??m th???y s???n ph???m !", "id",
					null);
		}

	}

	@SuppressWarnings("rawtypes")
	@PutMapping("/user/update/product-shop/options/images")
	public ResponseEntity updateProductOptionImage(@RequestBody Product_option_image product_option,
			HttpServletRequest req) {

		User us = new User();
		try {
			us = auth_service.getUserLogin(req.getHeader("Authorization"));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		Shop sh = shopService.findByUser(us);
		Product_option findProductOption = product_option_service.findById(product_option.getProductoption().getId());
		if (findProductOption != null) {
			if (sh.getId() != findProductOption.getProduct().getShop().getId()) {
				return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true,
						"B???n kh??ng ???????c ch???nh s???a s???n ph???m c???a h??ng kh??c !", "id", null);
			} else {
				product_option_image_service.deleteAllImage(product_option.getProductoption().getId());
				String[] words = product_option.getImage().split(",");
				for (int i = 0; i < words.length; i++) {
					product_option.setId(generateUUID().toString());
					product_option.setImage(words[i]);
					product_option_image_service.save(product_option);
				}
				return ResponseHandler.generateResponse(HttpStatus.OK, true, "C???p nh???t h??nh ???nh th??nh c??ng", "", null);
			}

		} else {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Kh??ng t??m th???y s???n ph???m !", "id",
					null);
		}
	}

	// TODO: java.lang.NullPointerException: Cannot invoke
	// "String.contains(java.lang.CharSequence)" because "token" is null
	@SuppressWarnings("rawtypes")
	@PutMapping("/user/enable/product-shop/")
	public ResponseEntity enableProductAndSell(@RequestBody Product product) {
		product.setEnable(true);
		return ResponseEntity.status(HttpStatus.OK).body(service.save(product));
	}

	@SuppressWarnings("rawtypes")
	@PutMapping("/user/update/product-shop/tag")
	public ResponseEntity updateProductTag(@RequestBody Product_tag product_tag, HttpServletRequest req) {

		User us = new User();
		try {
			us = auth_service.getUserLogin(req.getHeader("Authorization"));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		Shop sh = shopService.findByUser(us);
		Product_tag findProductTag = product_Tag_service.findById(product_tag.getId()).get();
		if (findProductTag != null) {
			if (sh.getId() != findProductTag.getProducttag().getShop().getId()) {
				return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true,
						"B???n kh??ng ???????c ch???nh s???a s???n ph???m c???a h??ng kh??c !", "id", null);
			} else {
				product_Tag_service.deleteAllTagByIdProduct(product_tag.getProducttag().getId());
				String[] words = product_tag.getTag().split(",");
				for (int i = 0; i < words.length; i++) {
					product_tag.setId(generateUUID().toString());
					product_tag.setTag(words[i]);
					product_Tag_service.save(product_tag);
				}
				return ResponseHandler.generateResponse(HttpStatus.OK, true, "C???p nh???t tag th??nh c??ng", "", null);
			}
		} else {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Kh??ng t??m th???y s???n ph???m !", "id",
					null);
		}

	}

}