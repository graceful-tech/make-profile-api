package com.make_profile.entity.history.candidates;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "candidate_experience_history_entity")
public class CandidateExperienceHistoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true, length = 200)
	private String companyName;

	@Column(nullable = true, length = 100)
	private String role;

	@Column(nullable = true)
	private String experienceYearStartDate;

	@Column(nullable = true)
	private String experienceYearEndDate;

	@Column(nullable = true)
	private boolean currentlyWorking;

	@Column(nullable = true, length = 1500)
	private String responsibilities;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "experience_id")
	private List<CandidateProjectHistoryEntity> projects;

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

	public boolean isCurrentlyWorking() {
		return currentlyWorking;
	}

	public void setCurrentlyWorking(boolean currentlyWorking) {
		this.currentlyWorking = currentlyWorking;
	}

	public String getResponsibilities() {
		return responsibilities;
	}

	public void setResponsibilities(String responsibilities) {
		this.responsibilities = responsibilities;
	}

	public List<CandidateProjectHistoryEntity> getProjects() {
		return projects;
	}

	public void setProjects(List<CandidateProjectHistoryEntity> projects) {
		this.projects = projects;
	}

}
