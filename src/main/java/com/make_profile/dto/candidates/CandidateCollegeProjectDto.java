package com.make_profile.dto.candidates;

import java.util.List;

public class CandidateCollegeProjectDto {

	private Long id;

	private String collegeProjectName;

	private String collegeProjectSkills;

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

	public String getCollegeProjectDescription() {
		return collegeProjectDescription;
	}

	public void setCollegeProjectDescription(String collegeProjectDescription) {
		this.collegeProjectDescription = collegeProjectDescription;
	}

	public String getCollegeProjectSkills() {
		return collegeProjectSkills;
	}

	public void setCollegeProjectSkills(String collegeProjectSkills) {
		this.collegeProjectSkills = collegeProjectSkills;
	}

	@Override
	public String toString() {
		return "{" + "id=" + id + ", collegeProjectName=" + collegeProjectName + ", collegeProjectSkills="
				+ collegeProjectSkills + ", collegeProjectDescription=" + collegeProjectDescription + "]";
	}

}
