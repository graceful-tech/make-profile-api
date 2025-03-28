package com.make_profile.repository.candidates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.candidates.JobScoreEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface JobScoreRepository extends JpaRepository<JobScoreEntity, Long> {

}
