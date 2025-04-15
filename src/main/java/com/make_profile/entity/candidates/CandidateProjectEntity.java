package com.make_profile.entity.candidates;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "candidate_project_details")
public class CandidateProjectEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true, length = 50)
	private String projectName;

	@Column(nullable = true, length = 1500)
	private String projectSkills;

	@Column(nullable = true, length = 50)
	private String projectRole;

	@Column(nullable = true, length = 2000)
	private String projectDescription;

	@ManyToOne
	@JoinColumn(name = "experience_id")
	private CandidateExperienceEntity candidateExperience;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectSkills() {
		return projectSkills;
	}

	public void setProjectSkills(String projectSkills) {
		this.projectSkills = projectSkills;
	}

	public String getProjectRole() {
		return projectRole;
	}

	public void setProjectRole(String projectRole) {
		this.projectRole = projectRole;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public CandidateExperienceEntity getCandidateExperience() {
		return candidateExperience;
	}

	public void setCandidateExperience(CandidateExperienceEntity candidateExperience) {
		this.candidateExperience = candidateExperience;
	}

}
