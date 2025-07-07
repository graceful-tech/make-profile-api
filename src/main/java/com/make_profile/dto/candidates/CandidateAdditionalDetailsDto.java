package com.make_profile.dto.candidates;

public class CandidateAdditionalDetailsDto {

	private Long id;

	private String stateName;

	private String preferredLocation;

	private Double currentCostToCompany;

	private Double expectedCostToCompany;

	private Double totalWorkExperience;

	private Double relevantExperience;

	private Long candidateId;

	private String companyName;

	private String qualification;

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
