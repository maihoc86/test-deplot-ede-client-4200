package com.ede.edeorderservice.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ede.edeorderservice.entity.Orderdetail;

public interface Order_Detail_service {

	Long getCountProductOder(String id);

	/**
	 * @author thái học
	 *
	 * 
	 */
	List<Orderdetail> searchOrderDetailAll(String keyword);

	/**
	 * @author thái học
	 * @param idOrder 
	 * @param pageRequest 
	 *
	 * 
	 */
	Page<Orderdetail> listAll(String idOrder, String keyword ,Pageable page);

	List<Orderdetail> findAllByUser(String idUser);

}
