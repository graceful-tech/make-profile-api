package com.make_profile.repository.candidates;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.candidates.CandidateEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CandidatesRepository extends JpaRepository<CandidateEntity, Long> {

	@Query(value = "select * from candidates where mobile_number  = :mobileNumber ", nativeQuery = true)
	CandidateEntity getCandidateDetailsByMobileNumber(@Param("mobileNumber") String mobileNumber);

	@Query(value = "select tenant from hurecom_v2.datasource_configuration where customer_id in (select id from hurecom_v2.customers where is_active ='Y') ", nativeQuery = true)
	List<String> getAllTenant();

	@Query(value = "select * from candidates where created_username = :createdUserName ", nativeQuery = true)
	CandidateEntity getCandidateByUserName(@Param("createdUserName") String createdUserName);

	@Query(value = "select id from candidates where created_username = :createdUserName ", nativeQuery = true)
	Long getCandidateIdByUserName(@Param("createdUserName") String createdUserName);

	@Modifying
	@Query(value = "insert into hurecom_v2.candidates(name,mobile_number,skills,email,gender,qualification,is_fresher) "
			+ "values(:name,:mobileNumber,:skills,:email,:gender,:qualification,:isFresher); ", nativeQuery = true)
	void saveCandidateInHurecomV2(@Param("name") String name, @Param("mobileNumber") String mobileNumber,
			@Param("skills") String skills, @Param("email") String email, @Param("gender") String gender,
			@Param("qualification") String qualification, @Param("isFresher") Boolean isFresher);

	@Modifying
	@Query(value = "update hurecom_v2.candidates set name = :name,mobile_number = :mobileNumber,skills = :skills,email = :email,is_fresher = :isFresher,gender = :gender where mobile_number =:mobileNumber  ", nativeQuery = true)
	void UpdateCandidateInHurecomV2(@Param("name") String name, @Param("mobileNumber") String mobileNumber,
			@Param("skills") String skills, @Param("email") String email, @Param("isFresher") boolean isFresher,
			@Param("gender") String gender);

	@Query(value = "select count(*) from hurecom_v2.candidates where mobile_number = :mobileNumber ", nativeQuery = true)
	int findCandidateByMobileNumber(@Param("mobileNumber") String mobileNumber);

	@Query(value = "select mobile_number from candidates where id  = :id ", nativeQuery = true)
	String getMobileNumberById(@Param("id") Long id);

	@Modifying
	@Query(value = "update hurecom_v2.candidates set preferred_location = :preferredLocation,relevant_experience = :relevantExperience,total_work_experience = :totalWorkExperience,current_cost_to_company = :currentCostToCompany,expected_cost_to_company = :expectedCostToCompany,company_name =:companyName,qualification = :qualification where mobile_number = :mobileNumber", nativeQuery = true)
	void UpdateCandidateProfessionalDetailsInHurecomV2(@Param("preferredLocation") String preferredLocation,
			@Param("relevantExperience") Double relevantExperience,
			@Param("totalWorkExperience") Double totalWorkExperience,
			@Param("currentCostToCompany") Double currentCostToCompany,
			@Param("expectedCostToCompany") Double expectedCostToCompany, @Param("companyName") String companyName,
			@Param("mobileNumber") String mobileNumber, @Param("qualification") String qualification);

}
