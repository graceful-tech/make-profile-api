package com.make_profile.dto.master;

import java.util.List;

public class CitiesDto {

	private Long id;

	private String cityName;

	private List<Long> stateIdList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public List<Long> getStateIdList() {
		return stateIdList;
	}

	public void setStateIdList(List<Long> stateIdList) {
		this.stateIdList = stateIdList;
	}

}
