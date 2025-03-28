package com.make_profile.service.master;

import com.make_profile.dto.WrapperDto;
import com.make_profile.dto.master.HurecomRequirementsDto;

public interface RequirementService {

	WrapperDto<HurecomRequirementsDto> hurecomRequirement(HurecomRequirementsDto hurecomRequirementsDto);

}
