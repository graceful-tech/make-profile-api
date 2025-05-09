package com.make_profile.dto.candidates;

import java.time.LocalDate;
import java.util.List;

public class CandidateExperienceDto {

	private Long id;

	private String companyName;

	private String role;

	private LocalDate experienceYearStartDate;

	private LocalDate experienceYearEndDate;

	private Boolean currentlyWorking;

	private Boolean isDeleted;

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

	public LocalDate getExperienceYearStartDate() {
		return experienceYearStartDate;
	}

	public void setExperienceYearStartDate(LocalDate experienceYearStartDate) {
		this.experienceYearStartDate = experienceYearStartDate;
	}

	public LocalDate getExperienceYearEndDate() {
		return experienceYearEndDate;
	}

	public void setExperienceYearEndDate(LocalDate experienceYearEndDate) {
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

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
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
				+ currentlyWorking + ", isDeleted=" + isDeleted + ", Responsibilities=" + Responsibilities
				+ ", projects=" + projects + +'}';
	}

//	@Override
//	public String toString() {
//		return "{" + "id=" + id + ", companyName='" + companyName + '\'' + ", role='" + role + '\''
//				+ ", experienceYearStartDate='" + experienceYearStartDate + '\'' + ", experienceYearEndDate='"
//				+ experienceYearEndDate + '\'' + ", currentlyWorking=" + currentlyWorking + ", projects=" + projects
//				+ '}';
//	}

}
