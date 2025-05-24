package com.make_profile.service.impl.openai;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.make_profile.dto.candidates.CandidateAchievementsDto;
import com.make_profile.dto.candidates.CandidateCertificatesDto;
import com.make_profile.dto.candidates.CandidateCollegeProjectDto;
import com.make_profile.dto.candidates.CandidateDto;
import com.make_profile.dto.candidates.CandidateExperienceDto;
import com.make_profile.dto.candidates.CandidateProjectDetailsDto;
import com.make_profile.dto.candidates.CandidateQualificationDto;
import com.make_profile.service.openai.ConvertJsonIntoCandidateDtoService;

@Service
public class ConvertJsonToCandidateDtoServiceImpl implements ConvertJsonIntoCandidateDtoService {

	private static final Logger logger = LoggerFactory.getLogger(ConvertJsonToCandidateDtoServiceImpl.class);

	@Autowired
	ModelMapper modelmapper;

	@Override
	public CandidateDto jsonToString(JsonObject jsonObject) {

		logger.debug("Service :: jsonToString :: Entered ");
		CandidateDto candidateDto = new CandidateDto();
		try {

			candidateDto.setFresher(false);

			JSONObject jSONObject = new JSONObject(jsonObject.toString());
			if (jSONObject.has("resume")) {
				jSONObject = (JSONObject) getValueFromJson(jSONObject, keyFromJson(jSONObject, "resume"));
			}

			String mobile = getValue("mobileNumber", jSONObject);
			mobile = mobile != "" ? mobile : getValue("phone", jSONObject);
			String name = getValue("name", jSONObject);
			String email = getValue("email", jSONObject);
			String nationality = getValue("nationality", jSONObject);
			String date_of_birth = getValue("dob", jSONObject);
			date_of_birth = date_of_birth != "" ? date_of_birth : getValue("date_of_birth", jSONObject);
			String address = getValue("address", jSONObject);
			String Language = getValue("language", jSONObject);
			String gender = getValue("gender", jSONObject);
			String maritalStatus = getValue("maritalStatus", jSONObject);
			String summary = getValue("summary", jSONObject);
			String careerObjective = getValue("careerObjective", jSONObject);

			String softSkills = getValue("softSkills", jSONObject);

			if (keyFromJson(jSONObject, "softSkills") != ""
					&& jSONObject.get(keyFromJson(jSONObject, "softSkills")) != null) {
				softSkills = softSkills != "" ? softSkills : getValue("softSkills", jSONObject);
			}

			String coreCompentencies = getValue("coreCompentencies", jSONObject);

			if (keyFromJson(jSONObject, "coreCompentencies") != ""
					&& jSONObject.get(keyFromJson(jSONObject, "coreCompentencies")) != null) {
				coreCompentencies = coreCompentencies != "" ? coreCompentencies
						: getValue("coreCompentencies", jSONObject);
			}

			// skills
			String skills = getValue("skills", jSONObject);
			skills = skills != "" ? skills : getValue("Primaryskills", jSONObject);
			if (keyFromJson(jSONObject, "skills") != "" && jSONObject.get(keyFromJson(jSONObject, "skills")) != null) {
				skills = skills != "" ? skills : getValue("skills", jSONObject);
			}

			// place
			String place = getValue("place", jSONObject);
			if (keyFromJson(jSONObject, "place") != "" && jSONObject.get(keyFromJson(jSONObject, "place")) != null) {
				place = place != "" ? place : getValue("place", jSONObject);
			}

			// Hobbies
			String hobbies = getValue("hobbies", jSONObject);
			if (keyFromJson(jSONObject, "hobbies") != ""
					&& jSONObject.get(keyFromJson(jSONObject, "hobbies")) != null) {
				hobbies = hobbies != "" ? hobbies : getValue("hobbies", jSONObject);
			}

			// EducationalQualification
			if (keyFromJson(jSONObject, "qualification") != ""
					&& jSONObject.get(keyFromJson(jSONObject, "qualification")) != null) {

				Object qualificationObj = jSONObject.get(keyFromJson(jSONObject, "qualification"));

				List<CandidateQualificationDto> qualificationList = new ArrayList<>();

				if (qualificationObj instanceof JSONArray) {
					JSONArray qualifications = (JSONArray) qualificationObj;

					for (int i = 0; i < qualifications.length(); i++) {
						JSONObject education = qualifications.getJSONObject(i);
						CandidateQualificationDto candidateQualificationDto = new CandidateQualificationDto();

						String institutionName = getValue("institutionName", education);
						String department = getValue("department", education);
						String qualificationStartYear = getValue("qualificationStartYear", education);
						String qualificationEndYear = getValue("qualificationEndYear", education);
						String percentage = getValue("percentage", education);
						String fieldOfStudy = getValue("fieldOfStudy", education);

						if (Objects.nonNull(institutionName) && !institutionName.isEmpty()) {
							candidateQualificationDto.setInstitutionName(institutionName);
						}

						if (Objects.nonNull(department) && !department.isEmpty()) {
							candidateQualificationDto.setDepartment(department);
						}

						if (Objects.nonNull(qualificationStartYear) && !qualificationStartYear.isEmpty()) {
							candidateQualificationDto.setQualificationStartYear(qualificationStartYear);
						}

						if (Objects.nonNull(qualificationEndYear) && !qualificationEndYear.isEmpty()) {
							candidateQualificationDto.setQualificationEndYear(qualificationEndYear);
						}

						if (Objects.nonNull(percentage) && !percentage.isEmpty()) {
							candidateQualificationDto.setPercentage(Double.valueOf(percentage));
						}

						if (Objects.nonNull(fieldOfStudy) && !fieldOfStudy.isEmpty()) {
							candidateQualificationDto.setFieldOfStudy(fieldOfStudy);
						}

						qualificationList.add(candidateQualificationDto);

						candidateQualificationDto = null;
					}
				}
				// Set this list to your main DTO
				candidateDto.setQualification(qualificationList);
			}

			// Experience
			if (keyFromJson(jSONObject, "experiences") != ""
					&& jSONObject.get(keyFromJson(jSONObject, "experiences")) != null) {

				Object experienceObj = jSONObject.get(keyFromJson(jSONObject, "experiences"));
				List<CandidateExperienceDto> experienceList = new ArrayList<>();

				if (experienceObj instanceof JSONArray) {
					JSONArray experiences = (JSONArray) experienceObj;

					for (int i = 0; i < experiences.length(); i++) {
						JSONObject experience = experiences.getJSONObject(i);
						CandidateExperienceDto candidateExperienceDto = new CandidateExperienceDto();

						String companyName = getValue("companyName", experience);
						String role = getValue("role", experience);
						String experienceYearStartDate = getValue("experienceYearStartDate", experience);
						String experienceYearEndDate = getValue("experienceYearEndDate", experience);
						String currentlyWorking = getValue("currentlyWorking", experience);
						String responsibilities = getValue("Responsibilities", experience);

						if (Objects.nonNull(companyName) && !companyName.isEmpty()) {
							candidateExperienceDto.setCompanyName(companyName);
						}

						if (Objects.nonNull(role) && !role.isEmpty()) {
							candidateExperienceDto.setRole(role);
						}

						if (Objects.nonNull(experienceYearStartDate) && !experienceYearStartDate.isEmpty()) {
							candidateExperienceDto.setExperienceYearStartDate(experienceYearStartDate);
						}

						if (Objects.nonNull(experienceYearEndDate) && !experienceYearEndDate.isEmpty()) {
							candidateExperienceDto.setExperienceYearEndDate(experienceYearEndDate);
						}

						if (Objects.nonNull(currentlyWorking) && !currentlyWorking.isEmpty()) {
							candidateExperienceDto.setCurrentlyWorking(Boolean.parseBoolean(currentlyWorking));
						}

						if (Objects.nonNull(responsibilities) && !responsibilities.isEmpty()) {
							candidateExperienceDto.setResponsibilities(responsibilities);
						}

						// Parse nested projects
						if (experience.has("projects")) {
							JSONArray projectsArray = experience.getJSONArray("projects");
							List<CandidateProjectDetailsDto> projectList = new ArrayList<>();

							for (int j = 0; j < projectsArray.length(); j++) {
								JSONObject project = projectsArray.getJSONObject(j);
								CandidateProjectDetailsDto projectDto = new CandidateProjectDetailsDto();

								String projectName = getValue("projectName", project);
								String projectRole = getValue("projectRole", project);
								String projectDescription = getValue("projectDescription", project);
								String projectSkills = getValue("projectSkills", project);

								if (Objects.nonNull(projectName) && !projectName.isEmpty()) {
									projectDto.setProjectName(projectName);
								}
								if (Objects.nonNull(projectRole) && !projectRole.isEmpty()) {
									projectDto.setProjectRole(projectRole);
								}
								if (Objects.nonNull(projectDescription) && !projectDescription.isEmpty()) {
									projectDto.setProjectDescription(projectDescription);
								}
								if (Objects.nonNull(projectSkills) && !projectSkills.isEmpty()) {
									projectDto.setProjectSkills(projectSkills);
								}

								projectList.add(projectDto);

								projectDto = null;
							}

							candidateExperienceDto.setProjects(projectList);
						}

						experienceList.add(candidateExperienceDto);
						candidateExperienceDto = null;
					}
				}
				candidateDto.setExperiences(experienceList);
			} else {
				candidateDto.setFresher(true);
			}

			if (keyFromJson(jSONObject, "certificates") != ""
					&& jSONObject.get(keyFromJson(jSONObject, "certificates")) != null) {

				Object certificateObj = jSONObject.get(keyFromJson(jSONObject, "certificates"));
				List<CandidateCertificatesDto> certificateList = new ArrayList<>();

				if (certificateObj instanceof JSONArray) {
					JSONArray certificates = (JSONArray) certificateObj;

					for (int i = 0; i < certificates.length(); i++) {
						JSONObject cert = certificates.getJSONObject(i);
						CandidateCertificatesDto certificateDto = new CandidateCertificatesDto();

						String courseName = getValue("courseName", cert);
						String courseStartDate = getValue("courseStartDate", cert);
						String courseEndDate = getValue("courseEndDate", cert);

						if (Objects.nonNull(courseName) && !courseName.isEmpty()) {
							certificateDto.setCourseName(courseName);
						}
						if (Objects.nonNull(courseStartDate) && !courseStartDate.isEmpty()) {
							certificateDto.setCourseStartDate(courseStartDate);
						}
						if (Objects.nonNull(courseEndDate) && !courseEndDate.isEmpty()) {
							certificateDto.setCourseEndDate(courseEndDate);
						}

						certificateList.add(certificateDto);

						certificateDto = null;
					}
				}

				candidateDto.setCertificates(certificateList);
			}

			if (keyFromJson(jSONObject, "achievements") != ""
					&& jSONObject.get(keyFromJson(jSONObject, "achievements")) != null) {

				Object achievementObj = jSONObject.get(keyFromJson(jSONObject, "achievements"));
				List<CandidateAchievementsDto> achievementList = new ArrayList<>();

				if (achievementObj instanceof JSONArray) {
					JSONArray achievements = (JSONArray) achievementObj;

					for (int i = 0; i < achievements.length(); i++) {
						JSONObject ach = achievements.getJSONObject(i);
						CandidateAchievementsDto achievementDto = new CandidateAchievementsDto();

						String achievementsName = getValue("achievementsName", ach);
						String achievementsDate = getValue("achievementsDate", ach);

						if (Objects.nonNull(achievementsName) && !achievementsName.isEmpty()) {
							achievementDto.setAchievementsName(achievementsName);
						}
						if (Objects.nonNull(achievementsDate) && !achievementsDate.isEmpty()) {
							achievementDto.setAchievementsDate(achievementsDate);
						}

						achievementList.add(achievementDto);

						achievementDto = null;
					}
				}
				candidateDto.setAchievements(achievementList);
			}

			if (keyFromJson(jSONObject, "collegeProject") != ""
					&& jSONObject.get(keyFromJson(jSONObject, "collegeProject")) != null) {

				Object collegeProjectObj = jSONObject.get(keyFromJson(jSONObject, "collegeProject"));

				List<CandidateCollegeProjectDto> collegeProjectList = new ArrayList<>();

				if (collegeProjectObj instanceof JSONArray) {
					JSONArray collegeProjects = (JSONArray) collegeProjectObj;

					for (int i = 0; i < collegeProjects.length(); i++) {
						JSONObject project = collegeProjects.getJSONObject(i);
						CandidateCollegeProjectDto collegeProjectDto = new CandidateCollegeProjectDto();

						String collegeProjectName = getValue("collegeProjectName", project);
						String collegeProjectDescription = getValue("collegeProjectDescription", project);

						String collegeProjectSkills = getValue("collegeProjectSkills", project);

						// Set values
						if (Objects.nonNull(collegeProjectName) && !collegeProjectName.isEmpty()) {
							collegeProjectDto.setCollegeProjectName(collegeProjectName);
						}
						if (Objects.nonNull(collegeProjectDescription) && !collegeProjectDescription.isEmpty()) {
							collegeProjectDto.setCollegeProjectDescription(collegeProjectDescription);
						}

						if (Objects.nonNull(collegeProjectSkills) && !collegeProjectSkills.isEmpty()) {
							collegeProjectDto.setCollegeProjectSkills(collegeProjectSkills);
						}

						collegeProjectList.add(collegeProjectDto);
						collegeProjectDto = null;
					}
				}

				// Set the list in CandidateDto
				candidateDto.setCollegeProject(collegeProjectList);
			}

			// Personal
			candidateDto.setName(name);
			candidateDto.setMobileNumber(mobile);

			if (Objects.nonNull(email) && !email.isEmpty()) {
				candidateDto.setEmail(email);
			}

			if (Objects.nonNull(nationality) && !nationality.isEmpty()) {
				candidateDto.setNationality(nationality);
			}

			if (Objects.nonNull(date_of_birth) && !date_of_birth.isEmpty()) {
				candidateDto.setDob(date_of_birth);
			}

			if (Objects.nonNull(address) && !address.isEmpty()) {
				candidateDto.setAddress(address);
			}

			if (Objects.nonNull(Language) && !Language.isEmpty()) {
				candidateDto.setLanguagesKnown(Language);
			}

			if (Objects.nonNull(gender) && !gender.isEmpty()) {
				candidateDto.setGender(gender);
			}

			if (Objects.nonNull(maritalStatus) && !maritalStatus.isEmpty()) {
				candidateDto.setMaritalStatus(maritalStatus);
			}

			candidateDto.setSummary(summary);
			candidateDto.setCareerObjective(careerObjective);

			if (Objects.nonNull(skills) && !skills.isEmpty()) {
				candidateDto.setSkills(skills);
			}

			if (Objects.nonNull(softSkills) && !softSkills.isEmpty()) {
				candidateDto.setSoftSkills(softSkills);
			}
			if (Objects.nonNull(coreCompentencies) && !coreCompentencies.isEmpty()) {
				candidateDto.setCoreCompentencies(coreCompentencies);
			}

		} catch (Exception e) {
			logger.debug("Service :: jsonToString :: Exception " + e.getMessage());
		}
		logger.debug("Service :: jsonToString :: Exited ");
		return candidateDto;

	}

	private String keyFromJson(JSONObject jsonObject, String expectedKey) {
		Iterator<String> keys = jsonObject.keys();
		String key = "";
		while (keys.hasNext()) {
			String key_s = keys.next();
			if (key_s.toLowerCase().startsWith(expectedKey.toLowerCase())) {
				key = key_s;
			} else {
				if (key_s.toLowerCase().endsWith(expectedKey.toLowerCase())) {
					key = key_s;
				}
			}
		}
		return key;
	}

	/*
	 
	 */
	private String getValue(String key, JSONObject jsonObject) {

		String value = "";
		if (jsonObject.has(keyFromJson(jsonObject, key))) {
			Object values = getValueFromJson(jsonObject, keyFromJson(jsonObject, key));
			// Object values = jsonObject.getString(keyFromJson(jsonObject, key));

			if (values instanceof String) {
				return (String) values;
			} else if (values instanceof String[]) {
				toString();
				value = (Arrays.asList(values)).stream().map(a -> String.valueOf(a)).collect(Collectors.joining(","));

			} else if (values instanceof JSONArray) {
				JSONArray jsonArray = (JSONArray) values;
				if (jsonArray.length() > 0 && jsonArray.get(0) instanceof JSONObject) {
					getValue(key, jsonArray.getJSONObject(0));
				} else if (jsonArray.length() > 0 && jsonArray.get(0) instanceof String) {
					toString();
					value = (Arrays.asList(jsonArray)).stream().map(a -> String.valueOf(a))
							.collect(Collectors.joining(","));
					// .replace("[", "").replace("]", "");
				}
			}

		} else {
			for (JSONObject jSONObject : findInnersJsonKeys(jsonObject)) {
				if (jSONObject.has(keyFromJson(jSONObject, key))) {
					Object values = getValueFromJson(jSONObject, keyFromJson(jSONObject, key));
					// Object values = jsonObject.getString(keyFromJson(jsonObject, key));
					if (values instanceof String) {
						return (String) values;
					} else if (values instanceof JSONArray) {
						JSONArray jsonArray = (JSONArray) values;
						if (jsonArray.length() > 0 && jsonArray.get(0) instanceof JSONObject) {
							getValue(key, jsonArray.getJSONObject(0));
						}
					}

				}
			}
		}

		return value;
	}

	private List<JSONObject> findInnersJsonKeys(JSONObject jsonObject) {
		List<JSONObject> innerJson = new ArrayList<>();
		findInnerJsonKeys(jsonObject, innerJson);
		return innerJson;
	}

	private JSONObject findInnerJsonKeys(JSONObject jsonObject, List<JSONObject> innerJson) {
		for (String key : jsonObject.keySet()) {
			Object value = jsonObject.get(key);

			if (value instanceof JSONObject) {
				innerJson.add((JSONObject) value);
				findInnerJsonKeys((JSONObject) value, innerJson);
			}
		}
		return jsonObject;
	}

	private JSONObject findUniqueJSONObject(JSONObject jSONObject, String key) {
		Object jsonObject = jSONObject.get(keyFromJson(jSONObject, key));
		if (jsonObject instanceof JSONObject) {
			return (JSONObject) jsonObject;
		} else if (jsonObject instanceof JSONArray) {
			JSONArray jsonArray = (JSONArray) jsonObject;
			if (jsonArray.length() > 0 && jsonArray.get(0) instanceof JSONObject) {
				return jsonArray.getJSONObject(0);
			}
		}

		return new JSONObject();
	}

	private static Object getValueFromJson(JSONObject jsonObject, String key) {
		Object value = jsonObject.opt(key);
		if (value instanceof String) {
			return value;
		} else if (value instanceof JSONArray) {
			return value;
		} else if (value instanceof String[]) {
			return value;
		}
		return null;
	}

	private List<String> getListFromString(String orginalValue) {
		ArrayList<String> stringToList = new ArrayList<>();
		if (orginalValue.contains("[") && orginalValue.contains("]")) {
			if (!orginalValue.contains("{")) {
				String value = orginalValue.replace("\\", "").replace("/", "");
				JSONArray jsonArray = new JSONArray(value);
				for (int i = 0; i <= jsonArray.length() - 1; i++) {
					stringToList.add(jsonArray.getString(i));
				}

			} else {
				stringToList.add(orginalValue);
			}
		} else {
			stringToList.add(orginalValue);

		}
		return stringToList;
	}

	private String validateMobileNumber(String num) {

		num = num == null ? "" : num.replace("+", "").replace("-", "").replace(" ", "");

		if (Pattern.compile("[a-zA-Z]+").matcher(num).find()) {

			num = num.replaceAll("[a-zA-Z]+", "");

		}
		if (num.length() > 9) {
			int size = num.length();
			num = num.substring(size - 10, size);
		}

		return num;

	}

}
