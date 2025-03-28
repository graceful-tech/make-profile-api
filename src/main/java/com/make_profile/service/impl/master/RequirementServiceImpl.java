package com.make_profile.service.impl.master;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.make_profile.dto.WrapperDto;
import com.make_profile.dto.master.HurecomRequirementsDto;
import com.make_profile.repository.master.RequirementRepository;
import com.make_profile.service.master.RequirementService;

@Service
public class RequirementServiceImpl implements RequirementService {

	private static final Logger logger = LoggerFactory.getLogger(RequirementServiceImpl.class);

	@Autowired
	RequirementRepository requirementRepository;

	@Override
	public WrapperDto<HurecomRequirementsDto> hurecomRequirement(HurecomRequirementsDto hurecomRequirementsDto) {
		logger.debug("Service :: hurecomRequirement :: Entered");

		WrapperDto<HurecomRequirementsDto> wrapperDto = null;
		try {
			Pageable pageable = PageRequest.of(hurecomRequirementsDto.getPage(), hurecomRequirementsDto.getLimit());
			wrapperDto = requirementRepository.hurecomRequirement(hurecomRequirementsDto, pageable);
		} catch (Exception e) {
			logger.debug("Service :: hurecomRequirement :: Exception" + e.getMessage());

		}
		logger.debug("Service :: hurecomRequirement :: Exited");
		return wrapperDto;
	}

}
