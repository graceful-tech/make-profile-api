package com.make_profile.repository.candidates;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.candidates.CandidateExperienceEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CandidateExperienceRepository extends JpaRepository<CandidateExperienceEntity, Long> {

	@Query(value = "select * from candidate_experience where id = :id", nativeQuery = true)
	CandidateExperienceEntity getExperienceById(@Param("id") Long id);

	@Query(value = "select * from candidate_experience where candidate_id = :candidateId", nativeQuery = true)
	List<CandidateExperienceEntity> getExperienceByCandidateId(@Param("candidateId") Long candidateId);

}
