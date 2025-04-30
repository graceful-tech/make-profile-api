package com.make_profile.service.impl.openai;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.make_profile.dto.candidates.CandidateDto;
import com.make_profile.dto.openai.ChatCompleitonResponse;
import com.make_profile.dto.openai.ChatCompletionRequest;
import com.make_profile.entity.candidates.CandidateEntity;
import com.make_profile.repository.candidates.CandidatesRepository;
import com.make_profile.service.candidates.CandidateService;
import com.make_profile.service.openai.ResumeDetailsAiService;
import com.make_profile.service.openai.ResumeJsonIntoStringService;

@Service
public class ResumeDetailsAiServiceImpl implements ResumeDetailsAiService {

	private static final Logger logger = LoggerFactory.getLogger(ResumeDetailsAiServiceImpl.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ResumeJsonIntoStringService resumeJsonIntoStringService;

	@Autowired
	CandidateService candidateService;

	@Autowired
	CandidatesRepository candidatesRepository;

	int count = 3;

	@Override
	public CandidateDto getUploadResumeDetialsFromAi(MultipartFile resume, String userName) {
		logger.debug("Service :: getUploadResumeDetialsFromAi :: Entered ");
		CandidateDto responseCandidateDto = null;
		try {

			String fileName = resume.getOriginalFilename() != "" || resume.getOriginalFilename() != null
					? resume.getOriginalFilename()
					: resume.getName();
			String message = "";
			if (fileName.endsWith(".txt")) {
				message = extractTextFromPlainText(fileName);
			} else {
				message = convertFileToText(resume);
			}

			String candidateDto = " {\r\n" + "  \"id\": \"\",\r\n" + "  \"name\": \"\",\r\n"
					+ "  \"mobileNumber\": \"\",\r\n" + "  \"alternateMobileNumber\": \"\",\r\n"
					+ "  \"email\": \"\",\r\n" + "  \"nationality\": \"\",\r\n" + "  \"gender\": \"\",\r\n"
					+ "  \"languagesKnown\": [],\r\n" + "  \"isFresher\": \"\",\r\n" + "  \"skills\": [],\r\n"
					+ "  \"linkedIn\": \"\",\r\n" + "  \"dob\": \"\",\r\n" + "  \"address\": \"\",\r\n"
					+ "  \"maritalStatus\": \"\",\r\n" + "  \"summary\":\"\",\r\n" + "  \"careerObjective:\"\";\r\n"
					+ "  \"experiences\": [\r\n" + "    {\r\n" + "      \"id\": \"\",\r\n"
					+ "      \"companyName\": \"\",\r\n" + "      \"role\": \"\",\r\n"
					+ "      \"experienceYearStartDate\": yyyy-MM-dd,\r\n"
					+ "      \"experienceYearEndDate\": yyyy-MM-dd,\r\n" + "      \"currentlyWorking\": \"\",\r\n"
					+ "      \"isDeleted\": \"\",\r\n" + "      \"Responsibilities\": [],\r\n"
					+ "      \"projects\": [\r\n" + "        {\r\n"
					+ "          // \"CandidateProjectDetailsDto\" placeholder\r\n" + "        }\r\n" + "      ]\r\n"
					+ "    }\r\n" + "  ],\r\n" + "  \"qualification\": [\r\n" + "    {\r\n" + "      \"id\": \"\",\r\n"
					+ "      \"instutionName\": \"\",\r\n" + "      \"department\": \"\",\r\n"
					+ "      \"qualificationStartYear\": yyyy-MM-dd,\r\n"
					+ "      \"qualificationEndYear\": yyyy-MM-dd,\r\n" + "      \"percentage\": \"\",\r\n"
					+ "      \"isDeleted\": \"\",\r\n" + "      \"fieldOfStudy\": \"\"\r\n" + "    }\r\n" + "  ],\r\n"
					+ "  \"certificates\": [\r\n" + "    {\r\n" + "      \"id\": \"\",\r\n"
					+ "      \"courseName\": \"\",\r\n" + "      \"courseStartDate\": yyyy-MM-dd,\r\n"
					+ "      \"courseEndDate\": yyyy-MM-dd,\r\n" + "      \"isDeleted\": \"\"\r\n" + "    }\r\n"
					+ "  ],\r\n" + "  \"achievements\": [\r\n" + "    {\r\n" + "      \"id\": \"\",\r\n"
					+ "      \"achievementsName\": \"\",\r\n" + "      \"achievementsDate\": yyyy-MM-dd,\r\n"
					+ "      \"isDeleted\": \"\"\r\n" + "    }\r\n" + "  ],\r\n" + "  \"candidateLogo\": \"\",\r\n"
					+ "  \"softSkills\": [],\r\n" + "  \"coreCompentencies\": [],\r\n" + "  \"score\": \"\",\r\n"
					+ "  \"matches\": \"\",\r\n" + "  \"collegeProject\": [\r\n" + "    {\r\n"
					+ "      \"id\": \"\",\r\n" + "      \"collegeProjectName\": \"\",\r\n"
					+ "      \"collegeProjectSkills\": [],\r\n" + "      \"collegeProjectDescription\": \"\",\r\n"
					+ "      \"isDeleted\": \"\"\r\n" + "    }\r\n" + "  ]\r\n" + "}";

			String query = " I will provide my resume content below along with an example DTO format above.\r\n"
					+ "Please:\r\n" + "\r\n"
					+ "Enhance the content by adding a professional summary, career objective, and relevant skills based on my resume information.\r\n"
					+ "\r\n" + "Convert all dates into LocalDate format (Java standard, e.g., \"2025-05-10\").\r\n"
					+ "\r\n" + "Ensure my mobile number is correctly captured and included.\r\n" + "\r\n"
					+ "Populate and return the filled DTO with the enhanced and corrected information." + "\r\n"
					+ "\r\n";

			ChatCompletionRequest chatRequest = new ChatCompletionRequest("gpt-4o-mini",
					candidateDto + query + message);

			ChatCompleitonResponse response = restTemplate.postForObject("https://api.openai.com/v1/chat/completions",
					chatRequest, ChatCompleitonResponse.class);

			responseCandidateDto = convertResponseString(response.getChoices().get(0).getMessage().getContent(), resume,
					userName);

		} catch (Exception e) {
			logger.debug("Service :: getUploadResumeDetialsFromAi :: Exception " + e.getMessage());
		}
		logger.debug("Service :: getUploadResumeDetialsFromAi :: Exited ");
		return responseCandidateDto;
	}

	private CandidateDto convertResponseString(String response, MultipartFile content, String userName)
			throws Exception {

		logger.debug("Service :: convertResponseString :: Entered ");

		CandidateDto responseCandidateDto = null;
		CandidateDto createCandidate = null;
		try {
			JsonObject jsonObject = null;
			String jsonString = response.substring(response.indexOf('{'), response.lastIndexOf('}'));
			JsonElement jsonElement = JsonParser.parseString(jsonString + "}");
			if (jsonElement.isJsonObject()) {
				jsonObject = jsonElement.getAsJsonObject();
				System.out.println("JSON Object: " + jsonObject.toString());
			}
			responseCandidateDto = resumeJsonIntoStringService.resumeJsonToString(jsonObject);

			responseCandidateDto.setCreatedUserName(userName);

			CandidateEntity candidateByUserName = candidatesRepository.getCandidateByUserName(userName);

			if (Objects.isNull(candidateByUserName)) {
				createCandidate = candidateService.createCandidate(responseCandidateDto);
			}

			responseCandidateDto = null;

		} catch (Exception e) {
			if (count > 0) {
				count--;
				getUploadResumeDetialsFromAi(content, userName);
			} else {
				logger.debug("Service :: convertResponseString :: Exception " + e.getMessage());
			}
		}
		logger.debug("Service :: convertResponseString :: Exited ");
		return createCandidate;

	}

	private String extractTextFromPlainText(String filePath) throws IOException {
		return new String(Files.readAllBytes(Paths.get(filePath)));
	}

	private String convertFileToText(MultipartFile file) {
		String text = null;
		logger.debug("Controller :: convertFileToText :: Entered");
		try {
			String fileName = FilenameUtils.getExtension(file.getOriginalFilename());

			if (fileName.equalsIgnoreCase("pdf")) {
				PDDocument pdfDocument = Loader.loadPDF(file.getInputStream().readAllBytes());
				PDFTextStripper pdfTextStripper = new PDFTextStripper();
				text = pdfTextStripper.getText(pdfDocument);
			} else if (fileName.equalsIgnoreCase("doc") || fileName.equalsIgnoreCase("docx")) {
				XWPFDocument wordDocument = new XWPFDocument(file.getInputStream());
				XWPFWordExtractor extractor = new XWPFWordExtractor(wordDocument);
				text = extractor.getText();
				extractor.close();
			}

		} catch (Exception e) {
			logger.error("Service :: convertFileToText :: Exception :: " + e.getMessage());
		}
		logger.debug("Controller :: convertFileToText :: Exited");
		return text;
	}

}
