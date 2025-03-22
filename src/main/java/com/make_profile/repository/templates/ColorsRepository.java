package com.make_profile.repository.templates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.templates.ColorEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface ColorsRepository extends JpaRepository<ColorEntity, Long> {

}
