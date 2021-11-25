package com.ede.edeorderservice.restcontroller.admin;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

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
import com.ede.edeorderservice.entity.Order_Discount;
import com.ede.edeorderservice.entity.Orderdetail;
import com.ede.edeorderservice.service.Order_Detail_service;
import com.ede.edeorderservice.service.Order_discount_service;

@RestController
@RequestMapping("/ede-order/admin")
@SuppressWarnings("rawtypes")
public class ReadOrderAdminRestController {
	@Autowired
	Order_discount_service service;
	
	@Autowired
	Order_Detail_service order_Detail_service;

	/**
	 * Hàm lấy tất cả giảm giá hóa đơn
	 * 
	 * @return {listObj}
	 * @throws ParseException
	 */
	@GetMapping("/getAll/discount/order")
	public ResponseEntity getAllDiscountOrder(@RequestParam(name = "searchTuNgay") String searchTuNgay,
			@RequestParam(name = "searchDenNgay") String searchDenNgay,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) throws ParseException {
		
		Page<Order_Discount> pageF = null;
		if ((!searchDenNgay.isEmpty() || searchTuNgay != "") && (!searchDenNgay.isEmpty() || searchDenNgay != "")) {
			System.err.print("Vào đây");
			pageF = service.listAllFilter(searchTuNgay, searchDenNgay, PageRequest.of(page, size));
		} else {
			pageF = service.listAllStatusTrue(PageRequest.of(page, size));
		}
		return ResponseEntity.ok(pageF);
	}

	/**
	 * Hàm lấy ra chi tiết hóa đơn nào sẽ được giảm giá theo điều kiện
	 * 
	 * @param id
	 * @return {obj}
	 */
	@GetMapping("/discount/order/{id}")
	public ResponseEntity getDiscountOrderById(@PathVariable("id") String id) {
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping("/getProductSellCurrentMonth")
	public List<Orderdetail> getProductSellCurrentMonth(){
		return order_Detail_service.getProductSellCurrentMonth(new Date());
	}
	
	@GetMapping("/getProductSell")
	public List<Orderdetail> getProductSell(){
		return order_Detail_service.getProductSell();
	}
}
