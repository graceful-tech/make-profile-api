package com.make_profile.repository.password;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.make_profile.entity.password.PasswordResetTokenEntity;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity, Long> {

	@Query(value = "SELECT * FROM password_reset_tokens WHERE user_id = :userId ORDER BY id DESC LIMIT 1", nativeQuery = true)
	PasswordResetTokenEntity findLastUserId(@Param("userId") Long userId);


}