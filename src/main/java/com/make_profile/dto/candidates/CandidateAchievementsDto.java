package com.make_profile.dto.candidates;

import java.time.LocalDate;

public class CandidateAchievementsDto {

	private Long id;

	private String achievementsName;

	private LocalDate achievementsDate;

	private Boolean isDeleted;

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

	public LocalDate getAchievementsDate() {
		return achievementsDate;
	}

	public void setAchievementsDate(LocalDate achievementsDate) {
		this.achievementsDate = achievementsDate;

	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "{" + "id=" + id + ", achievementsName='" + achievementsName + '\'' + ", achievementsDate='"
				+ achievementsDate + '\'' + '}';
	}
}
