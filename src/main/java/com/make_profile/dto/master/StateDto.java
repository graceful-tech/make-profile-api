package com.make_profile.dto.master;

import java.util.List;

public class StateDto {

	private Long id;

	private String stateName;

	private List<CitiesDto> cities;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public List<CitiesDto> getCities() {
		return cities;
	}

	public void setCities(List<CitiesDto> cities) {
		this.cities = cities;
	}

}
