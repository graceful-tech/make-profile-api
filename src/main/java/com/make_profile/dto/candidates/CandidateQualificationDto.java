package com.make_profile.dto.candidates;

import java.time.LocalDate;

public class CandidateQualificationDto {

	private Long id;

	private String institutionName;

	private String department;

	private String qualificationStartYear;

	private String qualificationEndYear;

	private Double percentage;

	private String fieldOfStudy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getQualificationStartYear() {
		return qualificationStartYear;
	}

	public void setQualificationStartYear(String qualificationStartYear) {
		this.qualificationStartYear = qualificationStartYear;
	}

	public String getQualificationEndYear() {
		return qualificationEndYear;
	}

	public void setQualificationEndYear(String qualificationEndYear) {
		this.qualificationEndYear = qualificationEndYear;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public String getFieldOfStudy() {
		return fieldOfStudy;
	}

	public void setFieldOfStudy(String fieldOfStudy) {
		this.fieldOfStudy = fieldOfStudy;
	}

	@Override
	public String toString() {
		return "{" + "id=" + id + ", institutionName=" + institutionName + ", department=" + department
				+ ", qualificationStartYear=" + qualificationStartYear + ", qualificationEndYear="
				+ qualificationEndYear + ", percentage=" + percentage + ", fieldOfStudy=" + fieldOfStudy + '}';
	}

}
