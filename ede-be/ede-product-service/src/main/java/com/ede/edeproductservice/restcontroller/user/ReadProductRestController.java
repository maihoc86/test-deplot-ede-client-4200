package com.ede.edeproductservice.restcontroller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.service.ProductService;

@RestController
@RequestMapping("/ede-product")
public class ReadProductRestController {
	
	@Autowired
	ProductService service;
	
	@GetMapping("/view/getAllProduct")
	public List<Product> getAllProduct() {
		return service.findAll();
	}
	
//	@Autowired
//	private EntityManager en;
	
	@GetMapping("/view/get-products/{keysearch}")
	public ResponseEntity<Object> getProducts(@PathVariable("keysearch") String keysearch) {
		Page<Product> result = this.service.searchByKeysearch(keysearch, PageRequest.of(0, 10));
// TODO Đánh dấu ở đây để mai code tiếp, chổ này cần phải nhanh chóng fix
//		List<Object> result = en.createQuery("SELECT p FROM ProductSearch p ORDER BY function('dbo.string_distance', p.keysearch, '"+keysearch+"') ASC")
//				.getResultList();
		
		System.err.println(result + "-----------");
		return ResponseEntity.ok(result);
	}
	
}