package com.make_profile.dto.candidates;

import java.util.List;

public class CandidateProjectDetailsDto {

	private Long id;

	private String projectName;

	private List<String> projectSkills;

	private String projectRole;

	private String projectDescription;

	private boolean isProjectDeleted;

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

	public List<String> getProjectSkills() {
		return projectSkills;
	}

	public void setProjectSkills(List<String> projectSkills) {
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

	public boolean isProjectDeleted() {
		return isProjectDeleted;
	}

	public void setProjectDeleted(boolean isProjectDeleted) {
		this.isProjectDeleted = isProjectDeleted;
	}

	@Override
	public String toString() {
		return "{" + "id=" + id + ", projectName='" + projectName + '\'' + ", projectSkills='" + projectSkills + '\''
				+ ", projectRole='" + projectRole + '\'' + ", projectDescription='" + projectDescription + '\'' + '}';
	}
}
