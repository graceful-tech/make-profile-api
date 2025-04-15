package com.make_profile.service.master;

import com.make_profile.dto.master.CreditsDto;

public interface CreditsService {

	CreditsDto getCredits(Long candidateId);

	CreditsDto addCredits(CreditsDto creditsDto);

	CreditsDto useCredit(CreditsDto creditsDto);

}
