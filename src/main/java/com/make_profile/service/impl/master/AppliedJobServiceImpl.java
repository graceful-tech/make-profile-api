package com.make_profile.service.impl.master;

import java.util.Arrays;
import java.util.Objects;

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

import com.make_profile.dto.candidates.HurecomAppliedJobsDto;
import com.make_profile.dto.candidates.HurecomCandidateDto;
import com.make_profile.dto.master.AppliedJobDto;
import com.make_profile.dto.master.HurecomRequirementsDto;
import com.make_profile.entity.candidates.CandidateEntity;
import com.make_profile.entity.master.AppliedJobsEntity;
import com.make_profile.repository.candidates.CandidatesRepository;
import com.make_profile.repository.master.AppliedJobRepository;
import com.make_profile.service.master.AppliedJobService;

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
		
		String hurecomEndPoint = "http://localhost:8080/candidates/create";
		String hurecomResponseEndPoint = "http://localhost:8080/applied-jobs/save";

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
				ResponseEntity<Long> candidateAppliedJobResponse = restTemplate.exchange(hurecomResponseEndPoint,
						HttpMethod.POST, responseEntity, Long.class);

				Long candidateAppliedJobId = candidateAppliedJobResponse.getBody();

				appliedJobsEntity.setCandidateId(appliedJobDto.getCandidateId());
				appliedJobsEntity.setJobId(appliedJobDto.getJobId());
				appliedJobsEntity.setTenant(appliedJobDto.getTenant());
				appliedJobsEntity.setSource("Hurecom");
				appliedJobsEntity.setSkills(candidateEntity.getSkills());
				appliedJobRepository.save(appliedJobsEntity);
				
				status = true;
			}

			hurecomCandidate = null;
			hurecomRequirementsDto = null;
			hurecomAppliedJobsDto = null;
			appliedJobsEntity = null;
		} catch (Exception e) {
			logger.debug("Service :: saveAppplication :: Exception" + e.getMessage());
		}
		logger.debug("Service :: saveAppplication :: Exited");
		return status;
	}

	public Long getRequirementId(String tenant, String jobId) {
		logger.debug("Service :: getJobDescription :: Entered");
		Long id = 0L;
		try {
			String jobDescriptionQuery = "select id from hurecom_" + tenant + ".requirements where job_id = '" + jobId
					+ "'";

			Query query = entityManager.createNativeQuery(jobDescriptionQuery);
			id = (Long) query.getSingleResult();
		} catch (Exception e) {
			logger.debug("Service :: getJobDescription :: Exception" + e.getMessage());
		}
		logger.debug("Service :: getJobDescription :: Exited");
		return id;
	}

	public String getUsernameByTenant(String tenant) {
		logger.debug("Service :: getJobDescription :: Entered");
		String userName = "";
		try {
			String jobDescriptionQuery = "select user_name from hurecom_" + tenant + ".users where id = 1 ";

			Query query = entityManager.createNativeQuery(jobDescriptionQuery);
			userName = (String) query.getSingleResult();
		} catch (Exception e) {
			logger.debug("Service :: getJobDescription :: Exception" + e.getMessage());
		}
		logger.debug("Service :: getJobDescription :: Exited");
		return userName;
	}

}
