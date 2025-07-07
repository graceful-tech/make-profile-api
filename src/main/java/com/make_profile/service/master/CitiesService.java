package com.make_profile.service.master;

import java.util.List;

import com.make_profile.dto.master.CitiesDto;
import com.make_profile.dto.master.StateDto;

public interface CitiesService {

	List<CitiesDto> retriveCities(CitiesDto citiesDto);

	List<StateDto> getAllStates();

}
