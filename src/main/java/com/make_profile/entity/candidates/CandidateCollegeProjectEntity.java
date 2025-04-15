package com.make_profile.entity.candidates;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "candidate_college_project")
public class CandidateCollegeProjectEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true)
	private String collegeProjectName;

	@Column(nullable = true , length=1500)
	private String collegeProjectSkills;

	@Column(nullable = true, length=2000)
	private String collegeProjectDescription;

	@ManyToOne
	@JoinColumn(name = "candidate_id")
	private CandidateEntity candidateId;

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

	public CandidateEntity getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(CandidateEntity candidateId) {
		this.candidateId = candidateId;
	}

}
