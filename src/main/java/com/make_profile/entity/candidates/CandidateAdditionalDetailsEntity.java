package com.make_profile.entity.candidates;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "candidate_additional_details")
public class CandidateAdditionalDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true, length = 50)
	private String preferredLocation;

	@Column(nullable = true, length = 50)
	private String stateName;

	@Column(nullable = true, length = 30)
	private Double currentCostToCompany;

	@Column(nullable = true, length = 10)
	private Double expectedCostToCompany;

	@Column(nullable = true, length = 10)
	private Double totalWorkExperience;

	@Column(nullable = true, length = 10)
	private Double relevantExperience;

	@Column(nullable = false, length = 10)
	private Long candidateId;

	@Column(nullable = true, length = 100)
	private String companyName;

	@Column(nullable = true, length = 100)
	private String qualification;

	@Column(nullable = true, length = 20)
	private String mobileNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPreferredLocation() {
		return preferredLocation;
	}

	public void setPreferredLocation(String preferredLocation) {
		this.preferredLocation = preferredLocation;
	}

	public Double getCurrentCostToCompany() {
		return currentCostToCompany;
	}

	public void setCurrentCostToCompany(Double currentCostToCompany) {
		this.currentCostToCompany = currentCostToCompany;
	}

	public Double getExpectedCostToCompany() {
		return expectedCostToCompany;
	}

	public void setExpectedCostToCompany(Double expectedCostToCompany) {
		this.expectedCostToCompany = expectedCostToCompany;
	}

	public Double getTotalWorkExperience() {
		return totalWorkExperience;
	}

	public void setTotalWorkExperience(Double totalWorkExperience) {
		this.totalWorkExperience = totalWorkExperience;
	}

	public Double getRelevantExperience() {
		return relevantExperience;
	}

	public void setRelevantExperience(Double relevantExperience) {
		this.relevantExperience = relevantExperience;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

}
