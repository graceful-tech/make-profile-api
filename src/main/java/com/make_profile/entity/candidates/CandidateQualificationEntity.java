package com.make_profile.entity.candidates;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "candidate_Qualification")
public class CandidateQualificationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true, length = 100)
	private String instutionName;

	@Column(nullable = true, length = 100)
	private String department;

	@Column(nullable = true)
	private LocalDate qualificationStartYear;

	@Column(nullable = true)
	private LocalDate qualificationEndYear;

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

	public String getFieldOfStudy() {
		return fieldOfStudy;
	}

	public void setFieldOfStudy(String fieldOfStudy) {
		this.fieldOfStudy = fieldOfStudy;
	}

}
