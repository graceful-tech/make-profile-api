package com.make_profile.service.master;

import java.util.List;

import com.make_profile.dto.master.CreditsDto;

public interface CreditsService {

	List<CreditsDto> getCredits(Long userId);

	boolean addCredits(CreditsDto creditsDto);

	boolean useCredit(CreditsDto creditsDto);

	Long getAvailableCredits(String templateName, Long userId);

}
