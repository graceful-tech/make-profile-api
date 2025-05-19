package com.make_profile.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.make_profile.entity.user.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	@Query(value = "select * from make_profile.user where email = :email ", nativeQuery = true)
	UserEntity findByEmail(String email);

	@Query(value = "select * from make_profile.user where mobile_number = :mobile_number ", nativeQuery = true)
	UserEntity findByMobileNumber(String mobile_number);

	@Query(value = "select count(id) from make_profile.user where email = :email or mobile_number = :mobileNumber ", nativeQuery = true)
	int findByMobileNumberAndEmail(String mobileNumber, String email);

	@Query(value = "select * from make_profile.user where user_name = :name ", nativeQuery = true)
	UserEntity findByUserName(String name);

	@Query(value = "select * from make_profile.user where name = :name ", nativeQuery = true)
	UserEntity findByName(String name);

}
