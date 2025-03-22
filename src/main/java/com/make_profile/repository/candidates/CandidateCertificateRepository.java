package com.make_profile.repository.candidates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.candidates.CandidateCertificateEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CandidateCertificateRepository extends JpaRepository<CandidateCertificateEntity, Long> {

	@Query(value = "select * from candidate_certification where id:id", nativeQuery = true)
	CandidateCertificateEntity getCertificateById(@Param("id") Long id);

}
