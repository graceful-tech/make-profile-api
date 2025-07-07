package com.make_profile.repository.master;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.master.CitiesEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CitiesRepository extends JpaRepository<CitiesEntity, Long> {

	@Query(value = "select * from cities where state_id in :stateId", nativeQuery = true)
	List<CitiesEntity> getCitiesFromStateId(@Param("stateId") List<Long> stateId);

}
