package com.make_profile.repository.impl.master;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.make_profile.dto.WrapperDto;
import com.make_profile.dto.master.HurecomRequirementsDto;
import com.make_profile.repository.candidates.CandidatesRepository;
import com.make_profile.repository.master.RequirementRepository;
import com.make_profile.service.impl.master.RequirementServiceImpl;
import com.make_profile.utility.CommonUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class RequirementRepositoryImpl implements RequirementRepository {

	private static final Logger logger = LoggerFactory.getLogger(RequirementServiceImpl.class);

	@Autowired
	CandidatesRepository candidatesRepository;

	@Autowired
	ModelMapper modelMapper;

	@PersistenceContext
	EntityManager entityManager;

 

	public static final BiFunction<HurecomRequirementsDto, List<Object>, String> get_requirements = (
			hurecomRequirementsDto, params) -> {

		StringBuilder sb = new StringBuilder();

		sb.append("select id, job_id, requirement_name,  designation, min_experience, max_experience, "
				+ "skills_required,status, locations, recruiters , max_cost_to_company from "
				+ "(select requirement.id, job_id, requirement_name, requirement.designation, min_experience, "
				+ "max_experience,skills_required,requirement.status, "
				+ "group_concat(distinct city.name separator ', ') locations, "
				+ "group_concat(distinct client_recruiter.name separator ', ') recruiters, requirement.max_cost_to_company "
				+ "from hurecom_"+ hurecomRequirementsDto.getTenant()+".requirements requirement "
				+ "left join hurecom_" +hurecomRequirementsDto.getTenant() +".applied_jobs applied_job on requirement.id = applied_job.requirement_id  "
				+ "left join hurecom_" +hurecomRequirementsDto.getTenant() +".clients client on client.id = requirement.client_id  "
				+ "left join hurecom_" +hurecomRequirementsDto.getTenant() +".requirement_locations requirement_location on requirement.id = requirement_location.requirement_id  "
				+ "left join hurecom_" +hurecomRequirementsDto.getTenant() +".client_locations client_location on client_location.id = requirement_location.client_location_id  "
				+ "left join hurecom_" +hurecomRequirementsDto.getTenant() +".countries country on client_location.country_id = country.id  "
				+ "left join hurecom_" +hurecomRequirementsDto.getTenant() +".states state on client_location.state_id = state.id  "
				+ "left join hurecom_" +hurecomRequirementsDto.getTenant() +".cities city on client_location.city_id = city.id  "
				+ "left join hurecom_" +hurecomRequirementsDto.getTenant() +".requirement_recruiters requirement_recruiter on requirement_recruiter.requirement_id = requirement.id "
				+ "left join hurecom_" +hurecomRequirementsDto.getTenant() +".client_recruiters client_recruiter on client_recruiter.id = requirement_recruiter.client_recruiter_id "
				+ "where requirement.status = 'Open' ");
		 


		sb.append("group by requirement.id, status_category) t where 1=1 ");

		sb.append("and job_id not in('XXXXX') ");

//		if (hurecomRequirementsDto.getRequirementIdList().size() > 0) {
//			sb.append("and id not in (" + hurecomRequirementsDto.getRequirementIdList().stream().map(String::valueOf)
//					.collect(Collectors.joining(",")) + ") ");
//		}

		if (Objects.nonNull(hurecomRequirementsDto.getDesignation())
				&& !hurecomRequirementsDto.getDesignation().isEmpty()) {
			sb.append("and upper(designation) like upper(?) ");
			params.add(CommonUtils.appendLikeOperator(hurecomRequirementsDto.getDesignation()));
		}

		if (Objects.nonNull(hurecomRequirementsDto.getLocations()) && !hurecomRequirementsDto.getLocations().isEmpty()) {
			sb.append("and upper(locations) like upper(?) ");
			params.add(CommonUtils.appendLikeOperator(hurecomRequirementsDto.getLocations()));
		}

		if (Objects.nonNull(hurecomRequirementsDto.getSkills()) && !hurecomRequirementsDto.getSkills().isEmpty()) {
			sb.append(" and " + hurecomRequirementsDto.getSkills().stream()
					.map(a -> "Upper(skills_required) like ('%" + a + "%') ").collect(Collectors.joining(" and ")));
			// params.add(searchAppliedJobDto.getSkills());
		}

		sb.append(" group by id, locations, recruiters");
		return sb.toString();
	};

	@Override
	public WrapperDto<HurecomRequirementsDto> hurecomRequirement(HurecomRequirementsDto hurecomRequirementsDto,
			Pageable pageable) {
		logger.debug("Repository :: hurecomRequirement :: Entered");

		WrapperDto<HurecomRequirementsDto> wrapperDto = new WrapperDto<HurecomRequirementsDto>();
		List<Object> params = new ArrayList<>();
		List<HurecomRequirementsDto> requirement = new ArrayList<>();
		try {

			List<String> allTenant = candidatesRepository.getAllTenant();

			allTenant.forEach(tenant -> {

				hurecomRequirementsDto.setTenant(tenant);

				Query query = entityManager.createNativeQuery(get_requirements.apply(hurecomRequirementsDto, params));

				int count = 1;
				for (Object param : params) {
					query.setParameter(count++, param);
				}

				query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
				query.setMaxResults(pageable.getPageSize());

				List<Object[]> requirements = query.getResultList();

				requirements.forEach(result -> {
					HurecomRequirementsDto hurecomrequirements = new HurecomRequirementsDto();

					hurecomrequirements.setId((Long) result[0]);
					hurecomrequirements.setJobId((String) result[1]);
					hurecomrequirements.setRequirementName((String) result[2]);
					hurecomrequirements.setDesignation((String) result[3]);
					hurecomrequirements.setMinExperience((Double) result[4]);
					hurecomrequirements.setMaxExperience((Double) result[5]);

					String skills = (String) result[6];
					hurecomrequirements.setSkills(Arrays.asList(skills.split(",")));
					hurecomrequirements.setStatus((String) result[7]);
					hurecomrequirements.setLocations((String) result[8]);
					hurecomrequirements.setClientRecruiters((String) result[9]);
					hurecomrequirements.setMaxCostToCompany((Double) result[10]);
					 
					hurecomrequirements.setSource("Hurecom");
					
					hurecomrequirements.setTenant(tenant);

					requirement.add(hurecomrequirements);
				});
				
				params.clear();

			});

			wrapperDto.setResults(requirement);
			wrapperDto.setTotalRecords(getRequirementsCount(hurecomRequirementsDto,allTenant));

		} catch (Exception e) {
			logger.debug("Repository :: hurecomRequirement :: Exception" + e.getMessage());
		}
		logger.debug("Repository :: hurecomRequirement :: Exited");
		return wrapperDto;
	}

	public static final BiFunction<HurecomRequirementsDto, List<Object>, String> get_requirement_count = (hurecomRequirementsDto, params) -> {

		StringBuilder sb = new StringBuilder();

		sb.append("select count(*) from "
				+ "(select requirement.id from hurecom_"+ hurecomRequirementsDto.getTenant()+".requirements requirement "
				+ "left join hurecom_" +hurecomRequirementsDto.getTenant() +".applied_jobs applied_job on requirement.id = applied_job.requirement_id  "
				+ "left join hurecom_" +hurecomRequirementsDto.getTenant() +".clients client on client.id = requirement.client_id  "
				+ "left join hurecom_" +hurecomRequirementsDto.getTenant() +".requirement_locations requirement_location on requirement.id = requirement_location.requirement_id  "
				+ "left join hurecom_" +hurecomRequirementsDto.getTenant() +".client_locations client_location on client_location.id = requirement_location.client_location_id  "
				+ "left join hurecom_" +hurecomRequirementsDto.getTenant() +".countries country on client_location.country_id = country.id  "
				+ "left join hurecom_" +hurecomRequirementsDto.getTenant() +".states state on client_location.state_id = state.id  "
				+ "left join hurecom_" +hurecomRequirementsDto.getTenant() +".cities city on client_location.city_id = city.id  "
				+ "left join hurecom_" +hurecomRequirementsDto.getTenant() +".requirement_recruiters requirement_recruiter on requirement_recruiter.requirement_id = requirement.id "
				+ "left join hurecom_" +hurecomRequirementsDto.getTenant() +".client_recruiters client_recruiter on client_recruiter.id = requirement_recruiter.client_recruiter_id "
				+ "where 1=1 ");
		 
		
		sb.append("and requirement.status = 'Open' ");

		sb.append("and requirement.job_id not in('XXXXX') ");

		if (Objects.nonNull(hurecomRequirementsDto.getDesignation())
				&& !hurecomRequirementsDto.getDesignation().isEmpty()) {
			sb.append("and upper(requirement.designation) like upper(?) ");
			params.add(CommonUtils.appendLikeOperator(hurecomRequirementsDto.getDesignation()));
		}

		if (Objects.nonNull(hurecomRequirementsDto.getLocations()) && !hurecomRequirementsDto.getLocations().isEmpty()) {
			sb.append("and upper(city.name) like upper(?) ");
			params.add(CommonUtils.appendLikeOperator(hurecomRequirementsDto.getLocations()));
		}

		if (Objects.nonNull(hurecomRequirementsDto.getSkills()) && !hurecomRequirementsDto.getSkills().isEmpty()) {
			sb.append(" and " + hurecomRequirementsDto.getSkills().stream()
					.map(a -> "Upper(requirement.skills_required) like Upper('%" + a + "%') ")
					.collect(Collectors.joining(" and ")));
			// params.add(searchAppliedJobDto.getSkills());
		}

		sb.append("group by requirement.id) t");

		return sb.toString();
	};

	private Long getRequirementsCount(HurecomRequirementsDto hurecomRequirementsDto,List<String> allTenant) {
		logger.debug("Repository :: getRequirementsCount :: Entered");

		Long totalRecords = 0L;
		List<Object> params = new ArrayList<>();
		try {
			for (String tenant : allTenant) {
				hurecomRequirementsDto.setTenant(tenant);
				Query query = entityManager.createNativeQuery(get_requirement_count.apply(hurecomRequirementsDto, params));

				int count = 1;
				for (Object param : params) {
					query.setParameter(count++, param);
				}
				totalRecords = totalRecords + (Long) query.getSingleResult();
				
				params.clear();
			}

		} catch (Exception e) {
			logger.error("Repository :: getRequirementsCount :: Exception :: " + e.getMessage());
		}
		logger.debug("Repository :: getRequirementsCount :: Exited");
		return totalRecords;
	}

}
