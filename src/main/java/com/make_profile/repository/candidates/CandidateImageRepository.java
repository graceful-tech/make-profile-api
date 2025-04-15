package com.make_profile.repository.candidates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.candidates.CandidateImageEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CandidateImageRepository extends JpaRepository<CandidateImageEntity, Long> {

}
