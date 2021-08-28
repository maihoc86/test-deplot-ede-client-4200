package com.ede.edeproductservice.service;

import com.ede.edeproductservice.entity.Shop;
import com.ede.edeproductservice.entity.User;

public interface ShopService {

	Shop findById(String string);

	Shop findByUser(User us);

}
