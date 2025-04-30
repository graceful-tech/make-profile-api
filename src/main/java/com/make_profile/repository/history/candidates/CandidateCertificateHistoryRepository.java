package com.make_profile.repository.history.candidates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.history.candidates.CandidateCertificatesHistoryEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CandidateCertificateHistoryRepository extends JpaRepository<CandidateCertificatesHistoryEntity, Long> {

}
