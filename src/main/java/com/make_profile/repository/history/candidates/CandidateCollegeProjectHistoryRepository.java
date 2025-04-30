package com.make_profile.repository.history.candidates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.history.candidates.CandidateCollegeProjectHistoryEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CandidateCollegeProjectHistoryRepository
		extends JpaRepository<CandidateCollegeProjectHistoryEntity, Long> {

}
