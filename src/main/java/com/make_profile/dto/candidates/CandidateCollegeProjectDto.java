package com.make_profile.dto.candidates;

import java.util.List;

public class CandidateCollegeProjectDto {

	private Long id;

	private String collegeProjectName;

	private List<String> collegeProjectSkills;

	private String collegeProjectDescription;

	private Boolean isDeleted;

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

	public List<String> getCollegeProjectSkills() {
		return collegeProjectSkills;
	}

	public void setCollegeProjectSkills(List<String> collegeProjectSkills) {
		this.collegeProjectSkills = collegeProjectSkills;
	}

	public String getCollegeProjectDescription() {
		return collegeProjectDescription;
	}

	public void setCollegeProjectDescription(String collegeProjectDescription) {
		this.collegeProjectDescription = collegeProjectDescription;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
