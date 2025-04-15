package com.make_profile.service.impl.openai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.make_profile.dto.requirement.fit.RequirementFitDto;
import com.make_profile.dto.requirement.fit.ResumeMatchResultDto;
import com.make_profile.service.openai.ExtractResultFromOpenAiService;

@Service
public class extractResultFromOpenAiServiceImpl implements ExtractResultFromOpenAiService {

	private static final Logger logger = LoggerFactory.getLogger(extractResultFromOpenAiServiceImpl.class);

	@Autowired
	ModelMapper modelmapper;

	@Override
	public ResumeMatchResultDto resultFromOpenAi(JsonObject jsonObject) {
		logger.debug("Service :: resultFromOpenAi :: Entered ");
		JSONObject jSONObject = new JSONObject(jsonObject.toString());

		ObjectMapper objectMapper = new ObjectMapper();

		ResumeMatchResultDto resumeMatchResultDto = new ResumeMatchResultDto();

		List<RequirementFitDto> requirementLists = new ArrayList<>();
		try {

			String matchString = getValue("match", jSONObject);
			boolean match = matchString != "" && matchString.equals("true") ? true : false;
			String matchScore = getValue("matchScore", jSONObject);
			String finalVerdict = getValue("finalVerdict", jSONObject);

			String improvementSuggestions = getValue("improvementSuggestions", jSONObject);

			if (keyFromJson(jSONObject, "improvementSuggestions") != ""
					&& jSONObject.get(keyFromJson(jSONObject, "improvementSuggestions")) != null) {

				improvementSuggestions = improvementSuggestions != "" ? improvementSuggestions
						: getValue("improvementSuggestions", jSONObject);
			}
			Object requirementMyresumeFit = getValueFromJson(jSONObject,
					keyFromJson(jSONObject, "requirementMyresumeFit"));

			// to convert object to ListOfDto
			if (Objects.nonNull(requirementMyresumeFit)) {
				requirementLists = objectMapper.readValue(requirementMyresumeFit.toString(),
						new TypeReference<List<RequirementFitDto>>() {
						});
			}
			resumeMatchResultDto.setMatch(match);
			resumeMatchResultDto.setMatchScore(matchScore);
			resumeMatchResultDto.setFinalVerdict(finalVerdict);
			resumeMatchResultDto.setImprovementSuggestions(getListFromString(improvementSuggestions));
			resumeMatchResultDto.setRequirementMyresumeFit(requirementLists);

		} catch (Exception e) {
			logger.debug("Service :: resultFromOpenAi :: Exception ");
		}
		logger.debug("Service :: resultFromOpenAi :: Exited ");
		return resumeMatchResultDto;
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
			} else if (values instanceof Boolean) {
				value = String.valueOf(values);
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
}
