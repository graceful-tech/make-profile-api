package com.make_profile.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.make_profile.entity.user.MakeProfileUserEntity;

@Repository
public interface MakeProfileUserRepository extends JpaRepository<MakeProfileUserEntity, Long> {

	@Query(value = "select * from make_profile.user where email = :email", nativeQuery = true)
	MakeProfileUserEntity findByEmail(@Param("email") String email);
	
	MakeProfileUserEntity findByMobileNumber(String number);

	MakeProfileUserEntity findByMobileNumberAndEmail(String mobileNumber, String email);

	@Query(value="select * from make_profile.user where name = :name",nativeQuery=true)
	MakeProfileUserEntity findByUserName(String name);

}
