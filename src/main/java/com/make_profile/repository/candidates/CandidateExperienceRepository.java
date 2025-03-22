package com.make_profile.repository.candidates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.candidates.CandidateExperienceEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CandidateExperienceRepository extends JpaRepository<CandidateExperienceEntity, Long> {

	@Query(value = "select * from candidate_experience where id:id", nativeQuery = true)
	CandidateExperienceEntity getExperienceById(@Param("id") Long id);

}
