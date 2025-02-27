package com.make_profile.dto.resume;

public class EducationDto {

	private String degree;

	private String institution;

	private String year;

	private String percentage;

	public String getDegree() {
		return degree;
	}

	public String getInstitution() {
		return institution;
	}

	public String getYear() {
		return year;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	@Override
	public String toString() {
		return "{" + "degree='" + degree + '\'' + ", institution='" + institution + '\'' + ", year=" + year
				+ ", percentage=" + percentage + '}';
	}

}
