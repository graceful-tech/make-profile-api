package com.make_profile.entity.history.candidates;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "candidates_qualification_history")
public class CandidateQualificationHistoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true, length = 100)
	private String institutionName;

	@Column(nullable = true, length = 100)
	private String department;

	@Column(nullable = true)
	private String qualificationStartYear;

	@Column(nullable = true)
	private String qualificationEndYear;

	@Column(nullable = true)
	private Double percentage;

	@Column(nullable = true)
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

}
