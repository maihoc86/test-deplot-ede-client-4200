package com.ede.edeproductservice.service;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.ede.edeproductservice.entity.Orderdetail;
import com.ede.edeproductservice.entity.Product_option;
import com.ede.edeproductservice.entity.extend.ProductSearch;
public interface Order_detail_service {

	List<Product_option> findAllOptionProductByIduser(String idUser, String id);
	Orderdetail findProductOption(String id);
	List<Object> findAllOptionProductInOrderDetailByIdShop(String id);
	List<Object> findAllOptionProductInOrderDetail();

}
