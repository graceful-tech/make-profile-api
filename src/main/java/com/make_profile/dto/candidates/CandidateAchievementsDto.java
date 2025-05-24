package com.make_profile.dto.candidates;

import java.time.LocalDate;

public class CandidateAchievementsDto {

	private Long id;

	private String achievementsName;

	private String achievementsDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAchievementsName() {
		return achievementsName;
	}

	public void setAchievementsName(String achievementsName) {
		this.achievementsName = achievementsName;
	}

	public String getAchievementsDate() {
		return achievementsDate;
	}

	public void setAchievementsDate(String achievementsDate) {
		this.achievementsDate = achievementsDate;
	}

	@Override
	public String toString() {
		return "{" + "id=" + id + ", achievementsName='" + achievementsName + '\'' + ", achievementsDate='"
				+ achievementsDate + '\'' + '}';
	}
}
