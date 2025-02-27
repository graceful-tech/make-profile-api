package com.make_profile.dto.resume;

import java.util.List;

public class ExperienceDto {

	private String role;

	private String company;

	private String duration;

	private List<String> responsibilities;

	public String getRole() {
		return role;
	}

	public String getCompany() {
		return company;
	}

	public String getDuration() {
		return duration;
	}

	public List<String> getResponsibilities() {
		return responsibilities;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public void setResponsibilities(List<String> responsibilities) {
		this.responsibilities = responsibilities;
	}
	
	@Override
	public String toString() {
		return "{" + "role='" + role + '\'' + ", company='" + company + '\'' + ", duration=" + duration
				+ ", responsibilities=" + responsibilities + '}';
	}

}
