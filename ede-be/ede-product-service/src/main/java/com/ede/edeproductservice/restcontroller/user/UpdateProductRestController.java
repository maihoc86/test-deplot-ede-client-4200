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
		// TODO sửa lại discount trên option
		// TODO tách hàm
		Order_detail_service findProductOption = order_detail_service
				.findProductOption(discount.getProductdiscount().getId());
		if (findProductOption != null) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true,
					"Sản phẩm này đã được bán, không được sửa giảm giá !", "id", null);
		} else {

			return ResponseEntity.status(HttpStatus.OK).body(product_discount_service.save(discount));
		}
	}

	/**
	 * Hàm cập nhật thuộc tính sản phẩm
	 * 
	 * @param product_option Thuộc tính sản phẩm
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
						"Bạn không được chỉnh sửa sản phẩm cửa hàng khác !", "id", null);
			} else {
				Order_detail_service findProductOptionOrder = order_detail_service
						.findProductOption(product_option.getId());
				if (findProductOptionOrder != null && product_option.getPrice() != findProductOption.getPrice()) {
					return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true,
							"Sản phẩm này đã được bán, không được sửa giá !", "id", null);
				} else {
					return ResponseEntity.status(HttpStatus.OK).body(product_option_service.save(product_option));
				}
			}
		} else {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Không tìm thấy sản phẩm !", "id",
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

		Product_option findProductOption = product_option_service.findById(product_option.getId());

		if (findProductOption != null) {
			if (sh.getId() != findProductOption.getProduct().getShop().getId()) {
				return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true,
						"Bạn không được chỉnh sửa sản phẩm cửa hàng khác !", "id", null);
			} else {
				product_option_image_service.deleteAllImage(product_option.getProductoption().getId());
				String[] words = product_option.getImage().split(",");
				for (int i = 0; i < words.length; i++) {
					product_option.setId(generateUUID().toString());
					product_option.setImage(words[i]);
					product_option_image_service.save(product_option);
				}
				return ResponseHandler.generateResponse(HttpStatus.OK, true, "Cập nhật hình ảnh thành công", "", null);
			}

		} else {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Không tìm thấy sản phẩm !", "id",
					null);
		}
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
						"Bạn không được chỉnh sửa sản phẩm cửa hàng khác !", "id", null);
			} else {
				product_Tag_service.deleteAllTagByIdProduct(product_tag.getProducttag().getId());
				String[] words = product_tag.getTag().split(",");
				for (int i = 0; i < words.length; i++) {
					product_tag.setId(generateUUID().toString());
					product_tag.setTag(words[i]);
					product_Tag_service.save(product_tag);
				}
				return ResponseHandler.generateResponse(HttpStatus.OK, true, "Cập nhật tag thành công", "", null);
			}
		} else {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Không tìm thấy sản phẩm !", "id",
					null);
		}

	}

}