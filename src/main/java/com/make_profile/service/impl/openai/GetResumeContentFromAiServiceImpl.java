package com.make_profile.service.impl.openai;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.make_profile.dto.candidates.ResumeContentDto;
import com.make_profile.dto.openai.ChatCompleitonResponse;
import com.make_profile.dto.openai.ChatCompletionRequest;
import com.make_profile.service.openai.GetResumeContentFromAiService;

@Service
public class GetResumeContentFromAiServiceImpl implements GetResumeContentFromAiService {

	private static final Logger logger = LoggerFactory.getLogger(GetResumeContentFromAiServiceImpl.class);

	@Autowired
	RestTemplate restTemplate;

	@Override
	public ResumeContentDto getResumeContent(String name) {
		logger.debug("Service :: getResumeContent :: Entered ");
		ResumeContentDto resumeContentDto = new ResumeContentDto();

		try {

			String contentDto = "\r\n" + "{\r\n" + "\r\n" + "  resumeContent :\"\";\r\n" + "  \r\n" + "\r\n" + "}";

			String constantMessage = "Give me the " + name
					+ " content that should adapt for both fresher and experience persons and then Attach that with the below Dto in resumeContent";

			ChatCompletionRequest chatRequest = new ChatCompletionRequest("gpt-4o-mini", constantMessage + contentDto);

			ChatCompleitonResponse response = restTemplate.postForObject("https://api.openai.com/v1/chat/completions",
					chatRequest, ChatCompleitonResponse.class);

			logger.debug("Service :: getResumeContent :: Exited ");

			resumeContentDto
					.setResumeContent(convertResponseString(response.getChoices().get(0).getMessage().getContent()));
		} catch (Exception e) {
			logger.error("Service :: getResumeContent :: Exception " + e.getMessage());
		}
		return resumeContentDto;

	}

	private String convertResponseString(String response) {
		logger.debug("Service :: convertResponseString :: Entered ");

		JsonObject jsonObject = null;
		String value = null;
		try {
			String jsonString = response.substring(response.indexOf('{'), response.lastIndexOf('}'));
			JsonElement jsonElement = JsonParser.parseString(jsonString + "}");
			if (jsonElement.isJsonObject()) {
				jsonObject = jsonElement.getAsJsonObject();
			}
			JSONObject jSONObject = new JSONObject(jsonObject.toString());

			value = getValue("resumeContent", jSONObject);

		} catch (Exception e) {
			logger.error("Service :: convertResponseString :: Exception " + e.getMessage());
		}
		logger.debug("Service :: convertResponseString :: Exited ");
		return value;

	}

	private String getValue(String key, JSONObject jsonObject) {
		logger.debug("Service :: getValue :: Entered ");

		String value = "";
		try {

			if (jsonObject.has(keyFromJson(jsonObject, key))) {
				Object values = getValueFromJson(jsonObject, keyFromJson(jsonObject, key));
				// Object values = jsonObject.getString(keyFromJson(jsonObject, key));

				if (values instanceof String) {
					return (String) values;
				} else if (values instanceof String[]) {
					toString();
					value = (Arrays.asList(values)).stream().map(a -> String.valueOf(a))
							.collect(Collectors.joining(","));

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

			}
		} catch (Exception e) {
			logger.error("Service :: getValue :: Exception " + e.getMessage());
		}
		logger.debug("Service :: getValue :: Exited ");
		return value;
	}

	private String keyFromJson(JSONObject jsonObject, String expectedKey) {
		logger.debug("Service :: keyFromJson :: Entered ");

		Iterator<String> keys = jsonObject.keys();
		String key = "";

		try {
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
		} catch (Exception e) {
			logger.error("Service :: keyFromJson :: Exception " + e.getMessage());
		}
		logger.debug("Service :: keyFromJson :: Exited ");

		return key;
	}

	private static Object getValueFromJson(JSONObject jsonObject, String key) {
		logger.debug("Service :: getValueFromJson :: Entered ");

		try {
			Object value = jsonObject.opt(key);
			if (value instanceof String) {
				return value;
			} else if (value instanceof JSONArray) {
				return value;
			} else if (value instanceof String[]) {
				return value;
			}
		} catch (Exception e) {
			logger.error("Service :: getValueFromJson :: Exception " + e.getMessage());
		}
		logger.debug("Service :: getValueFromJson :: Exited ");

		return null;
	}

}
