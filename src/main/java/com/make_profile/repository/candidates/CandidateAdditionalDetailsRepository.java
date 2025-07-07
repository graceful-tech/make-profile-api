package com.make_profile.repository.candidates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.candidates.CandidateAdditionalDetailsEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CandidateAdditionalDetailsRepository extends JpaRepository<CandidateAdditionalDetailsEntity, Long> {

	@Query(value = "select * from candidate_additional_details where candidate_id  = :candidateId ", nativeQuery = true)
	CandidateAdditionalDetailsEntity getAdditionalDetailsByCandidateId(@Param("candidateId") Long candidateId);

	@Query(value = "select * from candidate_additional_details where mobile_number  = :mobileNumber ", nativeQuery = true)
	CandidateAdditionalDetailsEntity getAdditionalDetailsByMobileNumber(@Param("mobileNumber") String mobileNumber);

}
