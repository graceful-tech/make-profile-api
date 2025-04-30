package com.make_profile.service.master;

import com.make_profile.dto.master.CreditsDto;

public interface CreditsService {

	CreditsDto getCredits(Long userId);

	boolean addCredits(CreditsDto creditsDto);

	boolean useCredit(CreditsDto creditsDto);

}
