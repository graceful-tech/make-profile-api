package com.make_profile.repository.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.master.AppliedJobsEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface AppliedJobRepository extends JpaRepository<AppliedJobsEntity, Long> {

	@Query(value = "select * from applied_jobs where candidate_id = :candidateId", nativeQuery = true)
	AppliedJobsEntity findByCandidateId(@Param("candidateId") Long candidateId);

}
