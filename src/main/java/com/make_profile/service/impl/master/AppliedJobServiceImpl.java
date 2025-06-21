package com.make_profile.service.impl.master;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.eclipse.persistence.internal.sessions.AggregateObjectChangeSet;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.make_profile.dto.ResponseDto;
import com.make_profile.dto.candidates.HurecomAppliedJobsDto;
import com.make_profile.dto.candidates.HurecomCandidateDto;
import com.make_profile.dto.master.AppliedJobDto;
import com.make_profile.dto.master.HurecomRequirementsDto;
import com.make_profile.entity.candidates.CandidateEntity;
import com.make_profile.entity.master.AppliedJobsEntity;
import com.make_profile.repository.candidates.CandidatesRepository;
import com.make_profile.repository.master.AppliedJobRepository;
import com.make_profile.service.master.AppliedJobService;

import io.jsonwebtoken.lang.Collections;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class AppliedJobServiceImpl implements AppliedJobService {

	private static final Logger logger = LoggerFactory.getLogger(AppliedJobServiceImpl.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	CandidatesRepository candidatesRepository;

	@Autowired
	ModelMapper modelMapper;

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	AppliedJobRepository appliedJobRepository;

	@Override
	public boolean saveAppplication(AppliedJobDto appliedJobDto) {
		logger.debug("Service :: saveAppplication :: Entered");

		boolean status = false;

		String hurecomEndPoint = "https://www.hurecom.com/hurecomv2rest/candidates/create";
		String hurecomResponseEndPoint = "https://www.hurecom.com/hurecomv2rest/applied-jobs/save";

		HurecomCandidateDto hurecomCandidateDto = null;
		HurecomCandidateDto hurecomCandidate = new HurecomCandidateDto();
		HurecomRequirementsDto hurecomRequirementsDto = new HurecomRequirementsDto();
		HurecomAppliedJobsDto hurecomAppliedJobsDto = new HurecomAppliedJobsDto();
		AppliedJobsEntity appliedJobsEntity = new AppliedJobsEntity();
		try {
			CandidateEntity candidateEntity = candidatesRepository.findById(appliedJobDto.getCandidateId()).get();

			if (Objects.nonNull(candidateEntity)) {
				hurecomCandidateDto = modelMapper.map(candidateEntity, HurecomCandidateDto.class);

				hurecomCandidateDto.setByCandidate(true);
				hurecomCandidateDto.setSkills(Arrays.asList(candidateEntity.getSkills().split(",")));
				hurecomCandidateDto.setLanguagesKnown(Arrays.asList(candidateEntity.getLanguagesKnown().split(",")));
				hurecomCandidateDto.setQualification(candidateEntity.getQualification().get(0).getFieldOfStudy() != null
						? candidateEntity.getQualification().get(0).getFieldOfStudy()
						: null);
				hurecomCandidateDto.setId(null);
			}

			String usernameByTenant = getUsernameByTenant(appliedJobDto.getTenant());

			HttpHeaders headers = new HttpHeaders();
			// TODO
			headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
			headers.add("tenant", appliedJobDto.getTenant());
			headers.add("UserName", usernameByTenant);

			HttpEntity<HurecomCandidateDto> entity = new HttpEntity<HurecomCandidateDto>(hurecomCandidateDto, headers);

			ResponseEntity<Long> response = restTemplate.exchange(hurecomEndPoint, HttpMethod.POST, entity, Long.class);

			Long hurecomCandidateId = response.getBody();

			if (Objects.nonNull(hurecomCandidateId) && hurecomCandidateId != 0) {

				hurecomCandidate.setId(hurecomCandidateId);
				hurecomAppliedJobsDto.setCandidate(hurecomCandidate);

				// TODO
				hurecomRequirementsDto.setId(getRequirementId(appliedJobDto.getTenant(), appliedJobDto.getJobId()));
				hurecomAppliedJobsDto.setRequirement(hurecomRequirementsDto);
				hurecomAppliedJobsDto.setStatusCategory("Not Processed");
				hurecomAppliedJobsDto.setStatus("Applied");

				HttpEntity<HurecomAppliedJobsDto> responseEntity = new HttpEntity<HurecomAppliedJobsDto>(
						hurecomAppliedJobsDto, headers);
				ResponseEntity<ResponseDto> candidateAppliedJobResponse = restTemplate.exchange(hurecomResponseEndPoint,
						HttpMethod.POST, responseEntity, ResponseDto.class);

				Long candidateAppliedJobId = candidateAppliedJobResponse.getBody().getId();

				appliedJobsEntity.setCandidateId(appliedJobDto.getCandidateId());
				appliedJobsEntity.setJobId(appliedJobDto.getJobId());
				appliedJobsEntity.setTenant(appliedJobDto.getTenant());
				appliedJobsEntity.setSource("Hurecom");
				appliedJobsEntity.setSkills(candidateEntity.getSkills());
				appliedJobsEntity.setStatus("Applied");
				appliedJobRepository.save(appliedJobsEntity);

				status = true;
			}

			hurecomCandidate = null;
			hurecomRequirementsDto = null;
			hurecomAppliedJobsDto = null;
			appliedJobsEntity = null;
		} catch (Exception e) {
			logger.error("Service :: saveAppplication :: Exception" + e.getMessage());
		}
		logger.debug("Service :: saveAppplication :: Exited");
		return status;
	}

	public Long getRequirementId(String tenant, String jobId) {
		logger.debug("Service :: getRequirementId :: Entered");
		Long id = 0L;
		try {
			String jobDescriptionQuery = "select id from hurecom_" + tenant + ".requirements where job_id = '" + jobId
					+ "'";

			Query query = entityManager.createNativeQuery(jobDescriptionQuery);
			id = (Long) query.getSingleResult();
		} catch (Exception e) {
			logger.error("Service :: getRequirementId :: Exception" + e.getMessage());
		}
		logger.debug("Service :: getRequirementId :: Exited");
		return id;
	}

	public String getUsernameByTenant(String tenant) {
		logger.debug("Service :: getUsernameByTenant :: Entered");
		String userName = "";
		try {
			String jobDescriptionQuery = "select user_name from hurecom_" + tenant + ".users where id = 1 ";

			Query query = entityManager.createNativeQuery(jobDescriptionQuery);
			userName = (String) query.getSingleResult();
		} catch (Exception e) {
			logger.error("Service :: getUsernameByTenant :: Exception" + e.getMessage());
		}
		logger.debug("Service :: getUsernameByTenant :: Exited");
		return userName;
	}

	@Override
	public List<AppliedJobDto> getAppliedJobs(Long candidateId) {
		logger.debug("Service :: getAppliedJobs :: Entered");

		List<AppliedJobsEntity> appliedJobsEntity = null;
		List<AppliedJobDto> appliedJobDtoList = new ArrayList<>();

		try {
			appliedJobsEntity = appliedJobRepository.findByCandidateId(candidateId);

			if (Objects.nonNull(appliedJobsEntity) && !Collections.isEmpty(appliedJobsEntity)) {

				appliedJobsEntity.forEach(jobs -> {
					AppliedJobDto appliedJobDto = new AppliedJobDto();

					appliedJobDto = modelMapper.map(jobs, AppliedJobDto.class);
					appliedJobDtoList.add(appliedJobDto);

					appliedJobDto = null;
				});
			}
			appliedJobsEntity = null;

		} catch (Exception e) {
			logger.error("Service :: getAppliedJobs :: Exception" + e.getMessage());
		}
		logger.debug("Service :: getAppliedJobs :: Exited");
		return appliedJobDtoList;
	}

}
