package com.make_profile.repository.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.master.CreditHistoryEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CreditHistoryRepository extends JpaRepository<CreditHistoryEntity, Long> {

}
