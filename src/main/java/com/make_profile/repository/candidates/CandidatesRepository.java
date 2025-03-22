package com.make_profile.repository.candidates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.candidates.CandidateEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CandidatesRepository extends JpaRepository<CandidateEntity, Long> {

	@Query(value = "select * from candidates where mobile_number  = :mobileNumber or alternate_mobile_number  = :mobileNumber ", nativeQuery = true)
	CandidateEntity getCandidateDetailsByMobileNumber(@Param("mobileNumber") String mobileNumber);

}
