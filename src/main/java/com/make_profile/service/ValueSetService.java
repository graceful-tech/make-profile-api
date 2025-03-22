package com.make_profile.service;

import java.util.List;

import com.make_profile.dto.ValueSetDto;

public interface ValueSetService {

	List<ValueSetDto> getValueSetsByCode(ValueSetDto valueSetDto);
}
