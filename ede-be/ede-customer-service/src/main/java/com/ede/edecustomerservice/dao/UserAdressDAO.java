package com.ede.edecustomerservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ede.edecustomerservice.entity.UserAddress;

public interface UserAdressDAO extends JpaRepository<UserAddress, String> {

	@Query("Select a from UserAddress a where a.user.id =:userId")
	List<UserAddress> getAllAddressByUser(String userId);

	@Query("Select a from UserAddress a where a.user.id =:userId and a.address =:address")
	UserAddress getAddressByUser(String userId, String address);

	@Query("Select a from UserAddress a where a.user.id =:userId and a.id =:addressId")
	UserAddress getAddressByUserId(String userId, String addressId);

}
