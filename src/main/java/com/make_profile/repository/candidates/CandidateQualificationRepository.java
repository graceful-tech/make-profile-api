package com.make_profile.repository.candidates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.candidates.CandidateQualificationEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CandidateQualificationRepository extends JpaRepository<CandidateQualificationEntity, Long> {

	@Query(value = "select * from candidate_Qualification where id:id", nativeQuery = true)
	CandidateQualificationEntity getQualificationById(@Param("id") Long id);
}
