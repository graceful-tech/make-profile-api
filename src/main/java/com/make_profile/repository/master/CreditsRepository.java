package com.make_profile.repository.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.master.CreditsEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CreditsRepository extends JpaRepository<CreditsEntity, Long> {

	CreditsEntity findByCandidateId(Long candidateId);
}
