package com.make_profile.dto.resume;

import java.util.List;

public class ProjectDto {

	private String name;

	private List<String> details;

	public String getName() {
		return name;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "{" + "name='" + name + '\'' + ", details='" + details + '}';
	}

}
