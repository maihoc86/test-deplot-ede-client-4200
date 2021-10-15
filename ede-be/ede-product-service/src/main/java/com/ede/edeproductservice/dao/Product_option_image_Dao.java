package com.ede.edeproductservice.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ede.edeproductservice.entity.Product_option_image;

public interface Product_option_image_Dao extends JpaRepository<Product_option_image, String> {

	@Query("SELECT o FROM Product_option_image o where o.productoption.id =:id")
	List<Product_option_image> findImageByIdOption(String id);

	@Transactional
	@Modifying
	@Query("DELETE FROM Product_option_image o where o.productoption.id =:id")
	void deleteAllImageByOptionId(String id);

}
