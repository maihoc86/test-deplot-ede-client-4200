/**
 * @author thái học
 *
 * 
 */
package com.ede.edeproductservice.restcontroller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edeproductservice.ResponseHandler;
import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.service.ProductService;

/**
 * @author thái học
 *
 * 
 */
@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/ede-product")
public class DeleteProductByAdminRestController {
	@Autowired
	ProductService service;

	@DeleteMapping("/product/delete/{id}")
	public ResponseEntity deleteProduct(@PathVariable("id") String id) {
		Product findById = service.findById(id);
		if (findById == null) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Sản phẩm không tồn tại", "Id", null);
		} else if (findById.getDeleted() == true) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Sản phẩm này đã bị xóa", "Id", null);
		} else {
			return ResponseEntity.ok(service.deleteById(id));
		}
	}
}
