package com.ede.edeproductservice.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ede.edeproductservice.dao.ProductDao;
import com.ede.edeproductservice.entity.Product;
import com.ede.edeproductservice.entity.Product_brand;
import com.ede.edeproductservice.entity.Product_child_category;
import com.ede.edeproductservice.entity.extend.ProductSearch;
import com.ede.edeproductservice.service.ProductService;

@Service
public class ProductImpl implements ProductService {

	/**
	 * This is ProductDao
	 */
	@Autowired
	ProductDao dao;

	@Override
	public Page<Product> listAll(Pageable pageable) {
		return dao.listAll(pageable);
	}

	/**
	 * Hàm search bằng từ khóa, hàm sẽ lấy ra product giống với từ khóa nhất (luôn
	 * có kết quả trả về)
	 * 
	 * @param keysearch   Từ khóa muốn search
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

	@Override
	public List<Product> findByShop(String id) {
		return dao.findByShop(id);
	}

	public Page<Product> listAll(PageRequest of) {
		return dao.listAll(of);
	}

	@Override
	public Page<ProductSearch> listAllProductShopByCustomer(String id, Pageable page) {
		return dao.listAllProductShopByCustomer(id, page);
	}

	/**
	 * @author thái học
	 *
	 * 
	 */
	@Override
	public Page<ProductSearch> filterProductShopByCustomerCategory(String valueCate, String id_shop, Pageable of) {
		return dao.filterProductShopByCustomerCategory(valueCate, id_shop, of);
	}

	/**
	 * @author thái học
	 *
	 * 
	 */
	@Override
	public Page<ProductSearch> filterProductShopByCustomerLocation(List<String> names, String id, Pageable of) {
		return dao.filterProductShopByCustomerLocation(names, id, of);
	}

	/**
	 * @author thái học
	 *
	 * 
	 */
	@Override
	public Page<ProductSearch> filterProductShopByCustomerLocationAndCategory(List<String> names, String category,
			String id, Pageable of) {
		return dao.filterProductShopByCustomerLocationAndCategory(names, category, id, of);
	}

	/**
	 * @author thái học
	 *
	 * 
	 */
	@Override
	public Page<ProductSearch> filterProductShopByCustomerLocationAndCategoryAndBrand(List<String> names,
			String category, List<String> brand, String id, Pageable of) {
		return dao.filterProductShopByCustomerLocationAndCategoryAndBrand(names, category, brand, id, of);
	}

	@Override
	public Page<ProductSearch> filterProductShopByCustomerBrand(List<String> valueBrand, String id, Pageable of) {
		return dao.filterProductShopByCustomerBrand(valueBrand, id, of);
	}

	/**
	 * @author thái học
	 *
	 * 
	 */
	@Override
	public Page<ProductSearch> filterProductShopByCustomerCategoryAndBrand(String category, List<String> valueBrand,
			String id, Pageable of) {
		return dao.filterProductShopByCustomerCategoryAndBrand(category, valueBrand, id, of);
	}

	@Override
	public Page<ProductSearch> filterProductShopByCustomerLocationAndBrand(List<String> names, List<String> brand,
			String id, Pageable of) {
		return dao.filterProductShopByCustomerLocationAndBrand(names, brand, id, of);
	}

	/**
	 * @author thái học
	 *
	 * 
	 */
	@Override
	public List<Product> listAll() {
		return dao.findAll();
	}

	/**
	 * @author thái học
	 *
	 * 
	 */
	@Override
	public Page<ProductSearch> listAllProductSearch(Pageable page) {
		return dao.listAllProductSearch(page);
	}

	/**
	 * @author thái học
	 *
	 * 
	 */
	@Override
	public Product deleteById(String id) {
		Product pr = dao.findById(id).get();
		pr.setDeleted(true);
		dao.save(pr);
		return pr;
	}
	@Override
	public ProductSearch findByProductSearchId(String id) {
		return dao.findByProductSearchId(id);
	}

	@Override
	public List<Product_brand> selectAllBrandInShop(String valueIdShop) {
		return dao.selectAllBrandInShop(valueIdShop);
	}

	@Override
	public Page<Product> findByCategory(String id, PageRequest pageRequest) {
		
		return dao.findByCategory(id,pageRequest);
	}

	@Override
	public Page<ProductSearch> filterProductShopByCustomerCategory2(String id, PageRequest pageRequest) {
		
		return dao.filterProductShopByCustomerCategory2(id,pageRequest);
	}

}
