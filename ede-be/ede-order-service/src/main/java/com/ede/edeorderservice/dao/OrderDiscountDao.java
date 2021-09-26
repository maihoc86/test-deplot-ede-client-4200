package com.ede.edeorderservice.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ede.edeorderservice.entity.Order_Discount;

public interface OrderDiscountDao extends JpaRepository<Order_Discount, String> {

	@Query("Select o from Order_Discount o where status = true")
	List<Order_Discount> listAllStatusTrue();

	@Query("Select o from Order_Discount o where enddate>:todate")
	Order_Discount findOrderDiscountDate(Date todate);

	@Query("Select o from Order_Discount o where status = true")
	Page<Order_Discount> listAllStatusTrue(PageRequest of);

	@Query(value="SELECT * FROM order_discount WHERE ?1 BETWEEN toDate AND endDate Or ?2 BETWEEN toDate AND endDate",nativeQuery = true)
	Page<Order_Discount> listAllFilter(@Param("searchTuNgay") String  searchTuNgayDate,@Param("searchDenNgay") String searchDenNgayDate, PageRequest of);

}
