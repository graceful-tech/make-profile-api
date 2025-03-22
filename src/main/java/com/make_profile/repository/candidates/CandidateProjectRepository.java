package com.make_profile.repository.candidates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.candidates.CandidateProjectEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CandidateProjectRepository extends JpaRepository<CandidateProjectEntity, Long> {

	@Query(value = "select * from candidate_project_details where id:id", nativeQuery = true)
	CandidateProjectEntity getProjectById(@Param("id") Long id);

}
