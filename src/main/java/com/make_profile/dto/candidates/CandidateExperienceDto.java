package com.make_profile.dto.candidates;

import java.time.LocalDate;
import java.util.List;

public class CandidateExperienceDto {

	private Long id;

	private String companyName;

	private String role;

	private String experienceYearStartDate;

	private String experienceYearEndDate;

	private Boolean currentlyWorking;

	private String Responsibilities;

	private List<CandidateProjectDetailsDto> projects;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getExperienceYearStartDate() {
		return experienceYearStartDate;
	}

	public void setExperienceYearStartDate(String experienceYearStartDate) {
		this.experienceYearStartDate = experienceYearStartDate;
	}

	public String getExperienceYearEndDate() {
		return experienceYearEndDate;
	}

	public void setExperienceYearEndDate(String experienceYearEndDate) {
		this.experienceYearEndDate = experienceYearEndDate;
	}

	public Boolean getCurrentlyWorking() {
		return currentlyWorking;
	}

	public void setCurrentlyWorking(Boolean currentlyWorking) {
		this.currentlyWorking = currentlyWorking;
	}

	public List<CandidateProjectDetailsDto> getProjects() {
		return projects;
	}

	public void setProjects(List<CandidateProjectDetailsDto> projects) {
		this.projects = projects;
	}

	public String getResponsibilities() {
		return Responsibilities;
	}

	public void setResponsibilities(String responsibilities) {
		Responsibilities = responsibilities;
	}

	@Override
	public String toString() {
		return "{" + "id=" + id + ", companyName=" + companyName + ", role=" + role + ", experienceYearStartDate="
				+ experienceYearStartDate + ", experienceYearEndDate=" + experienceYearEndDate + ", currentlyWorking="
				+ currentlyWorking + ", Responsibilities=" + Responsibilities + ", projects=" + projects + +'}';
	}

}
