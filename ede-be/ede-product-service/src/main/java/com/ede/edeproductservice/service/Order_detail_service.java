package com.ede.edeproductservice.service;
import java.util.List;

import com.ede.edeproductservice.entity.Orderdetail;
import com.ede.edeproductservice.entity.Product_option;
public interface Order_detail_service {

	List<Product_option> findAllOptionProductByIduser(String idUser, String id);
	Orderdetail findProductOption(String id);
}
