package com.make_profile.repository.templates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.templates.TemplateEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface TemplateRepository extends JpaRepository<TemplateEntity, Long> {
	
	@Query(value="select * from templates where template_name= :templateName",nativeQuery = true)
	TemplateEntity getTemplateByName(@Param("templateName") String templateName);

}
