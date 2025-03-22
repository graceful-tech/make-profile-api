package com.make_profile.repository.templates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.templates.UsedTemplateEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UsedTemplateRepository extends JpaRepository<UsedTemplateEntity, Long> {

}
