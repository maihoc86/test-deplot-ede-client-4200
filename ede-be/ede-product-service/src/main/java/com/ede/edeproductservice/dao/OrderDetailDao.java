package com.ede.edeproductservice.dao;




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ede.edeproductservice.entity.Orderdetail;
import com.ede.edeproductservice.entity.Product_option;


public interface OrderDetailDao extends JpaRepository<Orderdetail, String>{

	
	@Query("SELECT DISTINCT  o.productOption from Orderdetail o where o.order.user.id=:idUser and o.productOption.product.id=:id")
	List<Product_option> findAllOptionProductByIduser(String idUser, String id);

}
