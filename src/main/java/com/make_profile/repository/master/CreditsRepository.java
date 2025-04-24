package com.make_profile.repository.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.master.AppliedJobsEntity;
import com.make_profile.entity.master.CreditsEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CreditsRepository extends JpaRepository<CreditsEntity, Long> {

	@Query(value = "select * from credits where candidate_id = :candidateId", nativeQuery = true)
	CreditsEntity findCreditsByCandidateId(@Param("candidateId") Long candidateId);

}
