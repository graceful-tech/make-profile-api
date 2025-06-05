package com.make_profile.repository.history.candidates;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.history.candidates.CandidateHistoryEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CandidateHistoryRepository extends JpaRepository<CandidateHistoryEntity, Long> {

	@Query(value = "select count(*) from candidates_history where mobile_number = :mobileNumber", nativeQuery = true)
	int getCandidateCountMobileNumber(@Param("mobileNumber") String mobileNumber);

	@Query(value = "select * from candidates_history where candidate_id = :candidateId", nativeQuery = true)
	List<CandidateHistoryEntity> getCandidateHistoryByCandidateId(@Param("candidateId") Long candidateId);
}
