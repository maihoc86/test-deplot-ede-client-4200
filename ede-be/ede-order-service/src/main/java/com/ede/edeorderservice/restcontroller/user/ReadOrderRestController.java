package com.ede.edeorderservice.restcontroller.user;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edeorderservice.entity.Order;
import com.ede.edeorderservice.entity.Orderdetail;
import com.ede.edeorderservice.entity.Shop;
import com.ede.edeorderservice.service.Auth_Service;
import com.ede.edeorderservice.service.Order_Detail_service;
import com.ede.edeorderservice.service.Order_service;

@RestController
@RequestMapping("/ede-order")
@SuppressWarnings("rawtypes")
public class ReadOrderRestController {

	@Autowired
	Order_Detail_service order_detail_service;

	@Autowired
	Auth_Service authservice;

	@Autowired
	Order_service order_service;

	@Autowired
	HttpServletRequest req;

	@GetMapping("/view/countProductOder/{id}")
	public Long countProductOder(@PathVariable("id") String id) {
		return order_detail_service.getCountProductOder(id);
	}

	@GetMapping("/view/order/shop/search")
	public ResponseEntity listOrderSearch(@RequestParam("keyword") Optional<String> keyword,
			@RequestParam("status") Optional<String> status) {
		if (status.isPresent()) {
			return ResponseEntity.ok(order_service.searchOrderStatus(keyword.get(), status.get()));
		} else {
			return ResponseEntity.ok(order_service.searchOrderAll(keyword.get()));
		}
	}

	@GetMapping("/view/order_detail/shop/search")
	public ResponseEntity listOrderDetailSearch(@RequestParam("keyword") Optional<String> keyword) {
		return ResponseEntity.ok(order_detail_service.searchOrderDetailAll(keyword.get()));
	}

	@GetMapping("/view/order/shop/getAll")
	public ResponseEntity getAllOrder(@RequestParam(name = "keyword") String keyword, @RequestParam("status") Optional<String> status,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		Page<Order> pageLoad;
		Shop shop = new Shop();
		try {
			System.err.println(req.getHeader("Authorization"));
			shop = authservice.getShopLogin(req.getHeader("Authorization"));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		if (status.isPresent() && !status.get().isEmpty()) {
			pageLoad = order_service.findAllOrderShopByStatus(keyword,shop.getId(), status.get(), PageRequest.of(page, size));
		} else {
			pageLoad = order_service.findAllOrderByShop(keyword, shop.getId(), PageRequest.of(page, size));
		}
		pageLoad.stream().forEach(e -> {
			System.out.println(e);
		});
		return ResponseEntity.ok(pageLoad);
	}

	@GetMapping("/view/orderDetail/shop/getAll/{id}")
	public ResponseEntity getAll(@PathVariable("id") String idOrder,
			@RequestParam(name = "keyword") String keyword,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		Page<Orderdetail> pageLoad = order_detail_service.listAll(idOrder,keyword, PageRequest.of(page, size));
		return ResponseEntity.ok(pageLoad);
	}
}
