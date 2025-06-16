package com.make_profile.repository.password;

import org.springframework.data.jpa.repository.JpaRepository;

import com.make_profile.entity.password.PasswordResetTokenEntity;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity, Long> {

	PasswordResetTokenEntity findByUserId(Long id);

}