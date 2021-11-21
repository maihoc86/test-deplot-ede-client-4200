package com.ede.edecustomerservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ede.edecustomerservice.entity.User;

public interface UserDao extends JpaRepository<User, String> {

	User findByEmailLike(String email);

	User findByUsername(String username);

	User findByEmail(String email);

	User findByPhone(String phone);

	Long deleteByUsername(String username);

	List<User> findByUsernameContaining(String username);

	@Query("select o from User o where o.username=?1")
	User findByUsername2(String username);

	@Query(value = "Select * from users where DATEADD(DAY, DATEDIFF(DAY, 0, GETDATE()), 0) - DATEADD(DAY, DATEDIFF(DAY, 0, create_date), 0) < 7 ", nativeQuery = true)
	List<User> getNewUsers();

}
