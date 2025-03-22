package com.make_profile.repository.templates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.templates.TemplateAppliedEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface TemplateAppliedRepository extends JpaRepository<TemplateAppliedEntity, Long> {

	@Modifying
	@Query(value = "delete from candidate_qualification where candidate_id = :candidateId and section_name = :sectionName and section_id = :sectionId ", nativeQuery = true)
	void deleteByCandidateSectionId(@Param("candidateId") Long candidateId, @Param("sectionName") String sectionName,
			@Param("sectionId") Long sectoinId);
}
