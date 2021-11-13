package com.ede.edeproductservice.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ede.edeproductservice.entity.Orderdetail;
import com.ede.edeproductservice.entity.Product_option;
import com.ede.edeproductservice.entity.extend.ProductSearch;
import com.ede.edeproductservice.service.Order_detail_service;

public interface Order_detail_DAO extends JpaRepository<Orderdetail, String> {

	
	@Query("SELECT p FROM Orderdetail p where p.productOption.id =:id")
	Orderdetail findProductOption(String id);
	
	@Query("SELECT DISTINCT  o.productOption from Orderdetail o where o.order.user.id=:idUser and o.productOption.product.id=:id")
	List<Product_option> findAllOptionProductByIduser(String idUser, String id);
	@Query("select o.productOption.product.id,sum(o.quantity) from Orderdetail o group by o.productOption.product.id order by sum(o.quantity) desc")
	List<Object> findAllOptionProductInOrderDetailByIdShop(String id);

}
