package com.make_profile.repository.candidates;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.candidates.CandidateCollegeProjectEntity;
import com.make_profile.entity.candidates.CandidateExperienceEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CandidateCollegeProjectRepository extends JpaRepository<CandidateCollegeProjectEntity, Long> {

	@Query(value = "select * from candidate_college_project where id = :id", nativeQuery = true)
	CandidateCollegeProjectEntity getCollegeProjectById(@Param("id") Long id);

	@Query(value = "select * from candidate_college_project where candidate_id = :candidateId", nativeQuery = true)
	List<CandidateCollegeProjectEntity> getCollegeProjectByCandidateId(@Param("candidateId") Long candidateId);

}
