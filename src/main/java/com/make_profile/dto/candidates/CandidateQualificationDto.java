package com.make_profile.dto.candidates;

import java.time.LocalDate;

public class CandidateQualificationDto {

	private Long id;

	private String instutionName;

	private String department;

	private LocalDate qualificationStartYear;

	private LocalDate qualificationEndYear;

	private Double percentage;

	private Boolean isDeleted;

	private String fieldOfStudy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInstutionName() {
		return instutionName;
	}

	public void setInstutionName(String instutionName) {
		this.instutionName = instutionName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public LocalDate getQualificationStartYear() {
		return qualificationStartYear;
	}

	public void setQualificationStartYear(LocalDate qualificationStartYear) {
		this.qualificationStartYear = qualificationStartYear;
	}

	public LocalDate getQualificationEndYear() {
		return qualificationEndYear;
	}

	public void setQualificationEndYear(LocalDate qualificationEndYear) {
		this.qualificationEndYear = qualificationEndYear;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getFieldOfStudy() {
		return fieldOfStudy;
	}

	public void setFieldOfStudy(String fieldOfStudy) {
		this.fieldOfStudy = fieldOfStudy;
	}

	@Override
	public String toString() {
		return "{" + "id=" + id + ", instutionName=" + instutionName + ", department=" + department
				+ ", qualificationStartYear=" + qualificationStartYear + ", qualificationEndYear="
				+ qualificationEndYear + ", percentage=" + percentage + ", isDeleted=" + isDeleted + ", fieldOfStudy="
				+ fieldOfStudy + '}';
	}

}
