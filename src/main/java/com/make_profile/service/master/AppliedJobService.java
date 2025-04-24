package com.make_profile.service.master;

import com.make_profile.dto.master.AppliedJobDto;

public interface AppliedJobService {

	boolean saveAppplication(AppliedJobDto appliedJobDto);

	AppliedJobDto getAppliedJobs(Long candidateId);
}
