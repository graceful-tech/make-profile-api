package com.make_profile.service.impl.master;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.make_profile.dto.master.CitiesDto;
import com.make_profile.dto.master.StateDto;
import com.make_profile.repository.master.CitiesRepository;
import com.make_profile.repository.master.StatesRepository;
import com.make_profile.service.master.CitiesService;

@Service
public class CitiesServiceImpl implements CitiesService {

	private static final Logger logger = LoggerFactory.getLogger(CitiesServiceImpl.class);

	@Autowired
	CitiesRepository citiesRepository;

	@Autowired
	StatesRepository statesRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<CitiesDto> retriveCities(CitiesDto citiesDto) {
		logger.debug("Service :: retriveCities :: Entered");

		List<CitiesDto> citiesList = new ArrayList<>();
		try {
			if (Objects.nonNull(citiesDto) && !CollectionUtils.isEmpty(citiesDto.getStateIdList())) {
				citiesRepository.getCitiesFromStateId(citiesDto.getStateIdList()).forEach(citites -> {
					citiesList.add(modelMapper.map(citites, CitiesDto.class));
				});
			}

		} catch (Exception e) {
			logger.error("Service :: retriveCities :: Exception" + e.getMessage());
		}
		logger.debug("Service :: retriveCities :: Exited");
		return citiesList;
	}

	@Override
	public List<StateDto> getAllStates() {
		logger.debug("Service :: getAllStates :: Entered");

		List<StateDto> statesList = new ArrayList<>();
		try {
			statesRepository.findAll().forEach(states -> {
				statesList.add(modelMapper.map(states, StateDto.class));
			});
		} catch (Exception e) {
			logger.error("Service :: getAllStates :: Exception" + e.getMessage());
		}
		logger.debug("Service :: getAllStates :: Exited");
		return statesList;
	}

}
