package com.ede.edeorderservice.service;

import java.util.List;

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
	 *
	 * 
	 */
	List<Orderdetail> listAll();

}
