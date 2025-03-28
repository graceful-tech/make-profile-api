package com.make_profile.dto.master;

import java.util.List;

import com.make_profile.dto.BaseDto;

public class HurecomRequirementsDto extends BaseDto {

	private Long id;

	private String jobId;

	private String requirementName;

	private Long noOfPositions;

	private String designation;

	private String clientLocations;

	private String clientRecruiters;

	private String clientLevels;

	private Double minExperience;

	private Double maxExperience;

	private Double maxCostToCompany;

	private String skillsRequired;

	private List<String> skills;

	private String jobDescription;

	private String status;

	private Long billDurationInDays;

	private String typeOfCommission;

	private Double rateOfCommission;

	private String tenant;

	private String locations;

	private String source;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getRequirementName() {
		return requirementName;
	}

	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}

	public Long getNoOfPositions() {
		return noOfPositions;
	}

	public void setNoOfPositions(Long noOfPositions) {
		this.noOfPositions = noOfPositions;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getClientLocations() {
		return clientLocations;
	}

	public void setClientLocations(String clientLocations) {
		this.clientLocations = clientLocations;
	}

	public String getClientRecruiters() {
		return clientRecruiters;
	}

	public void setClientRecruiters(String clientRecruiters) {
		this.clientRecruiters = clientRecruiters;
	}

	public String getClientLevels() {
		return clientLevels;
	}

	public void setClientLevels(String clientLevels) {
		this.clientLevels = clientLevels;
	}

	public Double getMinExperience() {
		return minExperience;
	}

	public void setMinExperience(Double minExperience) {
		this.minExperience = minExperience;
	}

	public Double getMaxExperience() {
		return maxExperience;
	}

	public void setMaxExperience(Double maxExperience) {
		this.maxExperience = maxExperience;
	}

	public Double getMaxCostToCompany() {
		return maxCostToCompany;
	}

	public void setMaxCostToCompany(Double maxCostToCompany) {
		this.maxCostToCompany = maxCostToCompany;
	}

	public String getSkillsRequired() {
		return skillsRequired;
	}

	public void setSkillsRequired(String skillsRequired) {
		this.skillsRequired = skillsRequired;
	}

	public List<String> getSkills() {
		return skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getBillDurationInDays() {
		return billDurationInDays;
	}

	public void setBillDurationInDays(Long billDurationInDays) {
		this.billDurationInDays = billDurationInDays;
	}

	public String getTypeOfCommission() {
		return typeOfCommission;
	}

	public void setTypeOfCommission(String typeOfCommission) {
		this.typeOfCommission = typeOfCommission;
	}

	public Double getRateOfCommission() {
		return rateOfCommission;
	}

	public void setRateOfCommission(Double rateOfCommission) {
		this.rateOfCommission = rateOfCommission;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	public String getLocations() {
		return locations;
	}

	public void setLocations(String locations) {
		this.locations = locations;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
