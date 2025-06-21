package com.make_profile.repository.candidates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.candidates.CandidateEntity;
import com.make_profile.entity.candidates.CandidateImageEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CandidateImageRepository extends JpaRepository<CandidateImageEntity, Long> {

	@Query(value = "select * from candidate_images where candidate_id  = :candidateId ", nativeQuery = true)
	CandidateImageEntity getImageByCandidateId(@Param("candidateId") Long candidateId);

	@Query(value = "select file_name from candidate_images where candidate_id  = :candidateId ", nativeQuery = true)
	String getImageLocationByCandidateId(@Param("candidateId") Long candidateId);

}
