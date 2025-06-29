package com.make_profile.repository.history.candidates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.history.candidates.CandidateAchievementsHistoryEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CandidateAchievementsHistoryRepository
		extends JpaRepository<CandidateAchievementsHistoryEntity, Long> {

	

}
