package com.make_profile.dto.candidates;

import java.time.LocalDate;

public class CandidateCertificatesDto {

	private Long id;

	private String courseName;

	private LocalDate courseStartDate;

	private LocalDate courseEndDate;

	private Boolean isDeleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public LocalDate getCourseStartDate() {
		return courseStartDate;
	}

	public void setCourseStartDate(LocalDate courseStartDate) {
		this.courseStartDate = courseStartDate;
	}

	public LocalDate getCourseEndDate() {
		return courseEndDate;
	}

	public void setCourseEndDate(LocalDate courseEndDate) {
		this.courseEndDate = courseEndDate;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "{" + "id=" + id + ", courseName='" + courseName + '\'' + ", courseStartDate='" + courseStartDate + '\''
				+ ", courseEndDate='" + courseEndDate + '\'' + '}';
	}
}
