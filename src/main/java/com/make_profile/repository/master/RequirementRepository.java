package com.make_profile.repository.master;

import org.springframework.data.domain.Pageable;

import com.make_profile.dto.WrapperDto;
import com.make_profile.dto.master.HurecomRequirementsDto;

public interface RequirementRepository {

	WrapperDto<HurecomRequirementsDto> hurecomRequirement(HurecomRequirementsDto hurecomRequirementsDto,
			Pageable pageable);

}
