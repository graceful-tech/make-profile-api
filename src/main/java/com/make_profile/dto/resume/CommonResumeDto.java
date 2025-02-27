package com.make_profile.dto.resume;

import java.util.List;

public class CommonResumeDto {

	private String name;

	private String phone;

	private String email;

	private String jobTitle;

	private String linkedin;

	private String summary;

	private List<ExperienceDto> experiences;

	private List<ProjectDto> projects;

	private List<String> skills;

	private List<String> certifications;

	private List<EducationDto> education;

	private String dob;

	private String gender;

	private String languages;

	private List<String> softSkills;

	private String awards;

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public String getLinkedin() {
		return linkedin;
	}

	public String getSummary() {
		return summary;
	}

	public List<ExperienceDto> getExperiences() {
		return experiences;
	}

	public List<ProjectDto> getProjects() {
		return projects;
	}

	public List<String> getSkills() {
		return skills;
	}

	public List<String> getCertifications() {
		return certifications;
	}

	public List<EducationDto> getEducation() {
		return education;
	}

	public String getDob() {
		return dob;
	}

	public String getGender() {
		return gender;
	}

	public String getLanguages() {
		return languages;
	}

	public List<String> getSoftSkills() {
		return softSkills;
	}

	public String getAwards() {
		return awards;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setExperiences(List<ExperienceDto> experiences) {
		this.experiences = experiences;
	}

	public void setProjects(List<ProjectDto> projects) {
		this.projects = projects;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	public void setCertifications(List<String> certifications) {
		this.certifications = certifications;
	}

	public void setEducation(List<EducationDto> education) {
		this.education = education;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setLanguages(String languages) {
		this.languages = languages;
	}

	public void setSoftSkills(List<String> softSkills) {
		this.softSkills = softSkills;
	}

	public void setAwards(String awards) {
		this.awards = awards;
	}

}
