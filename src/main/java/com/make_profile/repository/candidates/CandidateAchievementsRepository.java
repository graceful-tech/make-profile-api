package com.make_profile.repository.candidates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.candidates.CandidateAchievementsEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CandidateAchievementsRepository extends JpaRepository<CandidateAchievementsEntity, Long> {

	@Query(value = "select * from candidate_achievements where id = :id", nativeQuery = true)
	CandidateAchievementsEntity getAchievementsById(@Param("id") Long id);

}
