package com.make_profile.repository.history.candidates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.history.candidates.CandidateQualificationHistoryEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CandidateQualificationHistoryRepository
		extends JpaRepository<CandidateQualificationHistoryEntity, Long> {

}
