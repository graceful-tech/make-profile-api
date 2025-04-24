package com.make_profile.repository.candidates;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.candidates.CandidateEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CandidatesRepository extends JpaRepository<CandidateEntity, Long> {

	@Query(value = "select * from candidates where mobile_number  = :mobileNumber ", nativeQuery = true)
	CandidateEntity getCandidateDetailsByMobileNumber(@Param("mobileNumber") String mobileNumber);

	@Query(value = "select tenant from hurecom_v2.datasource_configuration where customer_id in (select id from hurecom_v2.customers where is_active ='Y') ", nativeQuery = true)
	List<String> getAllTenant();

	@Query(value = "select * from candidates where created_username = :createdUserName ", nativeQuery = true)
	CandidateEntity getCandidateByUserName(@Param("createdUserName") String createdUserName);

}
