package com.make_profile.service.master;

import java.util.List;

import com.make_profile.dto.master.AppliedJobDto;

public interface AppliedJobService {

	boolean saveAppplication(AppliedJobDto appliedJobDto);

	List<AppliedJobDto> getAppliedJobs(Long candidateId);
}
