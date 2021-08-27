package com.ede.edeproductservice.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ede.edeproductservice.dao.ProductDao;
import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.entity.Product_child_category;
import com.ede.edeproductservice.entity.extend.ProductSearch;
import com.ede.edeproductservice.service.ProductService;


@Service
public class ProductImpl implements ProductService {

	@Autowired
	ProductDao dao;

	@Override
	public List<Product> findAll() {
		return dao.findAll();
	}

	/**
	 * Hàm search bằng từ khóa, hàm sẽ lấy ra product giống với từ khóa nhất (luôn có kết quả trả về)
	 * @param keysearch Từ khóa muốn search
	 * @param pageRequest Số lượng product muốn lấy ra
	 * @author Vinh
	 */
	@Override
	public Page<ProductSearch> searchByKeysearch(String keysearch, Pageable pageRequest) {
		return this.dao.searchBykeysearch(keysearch, pageRequest);
	}

	@Override
	public Product save(Product product) {
		return dao.save(product);
	}

	@Override
	public List<Product> findAllIsdeleteFalse() {	
		return dao.findAllByDeleted(false);
	}

	@Override
	public Product findById(String id) {	
		return dao.findById(id).get();
	}

	@Override
	public Product_child_category findCategorybyIDProduct(String id) {
		
		return dao.findCategorybyIDProduct(id);
	}
}
