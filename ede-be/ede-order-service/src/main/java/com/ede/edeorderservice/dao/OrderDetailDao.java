package com.ede.edeorderservice.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ede.edeorderservice.entity.Orderdetail;

public interface OrderDetailDao extends JpaRepository<Orderdetail, String>{

	@Query("select sum(o.quantity) from Orderdetail o where o.productOption.id =?1 ")
	Long getCountProductOder(String id);

	/**
	 * @author thái học
	 *
	 * 
	 */
	@Query("Select o from Orderdetail o where o.productOption.product.name like %:keyword% or o.order.user.username like %:keyword% ")
	List<Orderdetail> searchOrderDetailAll(String keyword);

	/**
	 * @author thái học
	 *
	 * 
	 */
	@Query("SELECT o FROM Orderdetail o where o.order.id=:idOrder")
	Page<Orderdetail> listAll(String idOrder, Pageable page);

}
