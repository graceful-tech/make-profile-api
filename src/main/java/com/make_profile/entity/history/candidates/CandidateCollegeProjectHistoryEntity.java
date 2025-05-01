package com.make_profile.entity.history.candidates;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "candidates_college_project_history")
public class CandidateCollegeProjectHistoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true)
	private String collegeProjectName;

	@Column(nullable = true, length = 1500)
	private String collegeProjectSkills;

	@Column(nullable = true, length = 2000)
	private String collegeProjectDescription;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCollegeProjectName() {
		return collegeProjectName;
	}

	public void setCollegeProjectName(String collegeProjectName) {
		this.collegeProjectName = collegeProjectName;
	}

	public String getCollegeProjectSkills() {
		return collegeProjectSkills;
	}

	public void setCollegeProjectSkills(String collegeProjectSkills) {
		this.collegeProjectSkills = collegeProjectSkills;
	}

	public String getCollegeProjectDescription() {
		return collegeProjectDescription;
	}

	public void setCollegeProjectDescription(String collegeProjectDescription) {
		this.collegeProjectDescription = collegeProjectDescription;
	}

}
