package com.make_profile.service.impl.openai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.make_profile.dto.openai.ChatCompleitonResponse;
import com.make_profile.dto.openai.ChatCompletionRequest;
import com.make_profile.service.openai.ExtractResultFromOpenAiService;
import com.make_profile.service.openai.MakeProfileOpenAiService;

@Service
public class MakeProfileOpenAiServiceImpl implements MakeProfileOpenAiService {

	private static final Logger logger = LoggerFactory.getLogger(MakeProfileOpenAiServiceImpl.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ExtractResultFromOpenAiService extractResultFromOpenAiService;

	int count = 3;

	@Override
	public void makeProfileAi(String content) {

		logger.debug("Service :: makeProfileAi :: Entered ");

		try {
			String constantMessage = "here i am provding job description of which i going to apply to company and for your "
					+ "identification i am adding company job description start and job description end  as same as job "
					+ "description end i am going to provide my resume in spring free marker in ftl format can you, analyse"
					+ " company job description and my resume content can you provide score for this resume and tell me whether is suite or not";

			String des = "job description start\r\n" + "\r\n" + "Job Role: Sr. Backend Engineer\r\n"
					+ "Key Skills: Core Java + Springboot\r\n" + "Location: Bengaluru/ Remote\r\n"
					+ "Department: Data Engineering/ IT\r\n" + "Job Type: Full Time\r\n" + "\r\n"
					+ "Position Overview\r\n" + " \r\n"
					+ "We are seeking an experienced Backend Java Engineer to join our Engineering team. The ideal candidate will have a strong background. As a contributing hands-on engineer in the Data & Analytics Platform, you will Lead all aspects of Software Development Lifecyle (SDLC) in-line with Agile and IT craftsmanship principles, Design client-side and server-side architecture & solutions, Write secure & scalable clean code along with unit tests, perform code reviews and ensure the quality of code constantly, develop and manage well-functioning databases and applications. Constantly learn new/emerging technologies and mentor teams and troubleshoot, debug and upgrade software.\r\n"
					+ "\r\n" + "Technical Expertise:\r\n" + " \r\n"
					+ "JavaScript and relevant frameworks React, Vue, Node\r\n"
					+ "Java (version 11 or higher), TDD, BDD\r\n"
					+ "Spring ecosystem (Core, MVC, Data, JPA, Transaction Management, Security, AOP, Spring Batch)\r\n"
					+ "Reactive programming & framework\r\n" + "SOA/Microservices (REST, SOAP)\r\n"
					+ "Messaging & Pub-Sub technologies (MQ, Kafka)\r\n" + "Writing complex SQL scripts\r\n"
					+ "Relational & non-relational databases (PostgreSQL, Oracle, Cosmos, MongoDB)\r\n"
					+ "Tomcat/Weblogic servers, serverless (cloud) deployment, Kubernetes/Docker\r\n"
					+ "Create and maintain CI/CD pipelines using GitHub Actions / Azure DevOps\r\n"
					+ "Building applications using cloud platforms (Azure/AWS/GCP)\r\n"
					+ "Creating Infrastructure as Code using Terraform\r\n" + "Bonus:\r\n" + " \r\n"
					+ "Azure Developer certification\r\n"
					+ "Experience of Master Data Management (MDM) domain & related tools (e.g., Informatica)\r\n"
					+ "Experience with Elastic Search / ELK stack, Python\r\n" + "\r\n" + "job description end ";

			String expampleJsonFormat = " give the result in below json format" + " \n{\n" + "\"match\":true,\r\n"
					+ "\"match_score\":\"70%\",\r\n" + "\"requirementMyresumeFit\":[\r\n" + "	{\r\n"
					+ "	\"requirement\":\"Xyz\",\r\n" + "	\"myResume\":\"Xyz\",\r\n" + "	\"match\":\"Xyz\",\r\n"
					+ "	},\r\n" + "	{\r\n" + "	\"requirement\":\"Xyo\",\r\n" + "	\"myResume\":\"Xyo\",\r\n"
					+ "	\"Match\":\"Xyo\",\r\n" + "	}\r\n" + "	\r\n" + "	],\r\n" + "\r\n" + "\r\n"
					+ "\"improvementSuggestions\":[\"dfg\",\"ert\"],\r\n" + "\"finalVerdict\":\"fssg\"}";

			ChatCompletionRequest chatRequest = new ChatCompletionRequest("gpt-4o-mini",
					constantMessage + des + content + expampleJsonFormat);

			ChatCompleitonResponse response = restTemplate.postForObject("https://api.openai.com/v1/chat/completions",
					chatRequest, ChatCompleitonResponse.class);

			convertResponseString(response.getChoices().get(0).getMessage().getContent());
			// convertResponseString(response.toString());

		} catch (Exception e) {

			logger.debug("Service :: makeProfileAi :: Exception " + e.getMessage());

		}

		logger.debug("Service :: makeProfileAi :: Exited ");

	}

	private void convertResponseString(String response) throws Exception {

		logger.debug("Service :: convertResponseString :: Entered ");

		// MultipartFile forCatchBlock = message;
		try {
			JsonObject jsonObject = null;
			String jsonString = response.substring(response.indexOf('{'), response.lastIndexOf('}'));
			JsonElement jsonElement = JsonParser.parseString(jsonString + "}");
			if (jsonElement.isJsonObject()) {
				jsonObject = jsonElement.getAsJsonObject();
				System.out.println("JSON Object: " + jsonObject.toString());
			}
			extractResultFromOpenAiService.resultFromOpenAi(jsonObject);
		} catch (Exception e) {
			if (count > 0) {
				count--;
				// makeProfileAi();

			} else {
				logger.debug("Service :: convertResponseString :: Exception " + e.getMessage());
			}

		}

		logger.debug("Service :: convertResponseString :: Exited ");

	}

}
