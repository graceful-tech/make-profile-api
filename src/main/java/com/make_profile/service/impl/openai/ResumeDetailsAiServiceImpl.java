package com.make_profile.service.impl.openai;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

	int count = 3;

	@Override
	public CandidateDto getUploadResumeDetialsFromAi(MultipartFile resume) {
		logger.debug("Service :: getUploadResumeDetialsFromAi :: Entered ");
		CandidateDto responseCandidateDto = null;
		try {

			String fileName = resume.getOriginalFilename() == "" || resume.getOriginalFilename() == null
					? resume.getOriginalFilename()
					: resume.getName();
			String message = "";
			if (fileName.endsWith(".txt")) {
				message = extractTextFromPlainText(fileName);
			} else {
				message = convertFileToText(resume);
			}

			String candidateDto = "{ \r\n" + "  id: \"\", \r\n" + "  name: \"\", \r\n" + "  mobileNumber: \"\", \r\n"
					+ "  email: \"\", \r\n" + "  nationality: \"\", \r\n" + "  gender: \"\", \r\n"
					+ "  languagesKnown: [], \r\n" + "  isFresher: \"\", \r\n" + "  skills: [], \r\n"
					+ "  linkedIn: \"\", \r\n" + "  dob: \"\", \r\n" + "  address: \"\", \r\n"
					+ "  maritalStatus: \"\", \r\n" + "  summary:\"\", \r\n" + "  careerObjective:\"\"; \r\n"
					+ "  experiences: [ \r\n" + "	{ \r\n" + "	  id: \"\", \r\n" + "	  companyName: \"\", \r\n"
					+ "	  role: \"\", \r\n" + "	  experienceYearStartDate: \"\", \r\n"
					+ "	  experienceYearEndDate: \"\", \r\n" + "	  currentlyWorking: false, \r\n"
					+ "	  isDeleted: false, \r\n" + "	  Responsibilities: [], \r\n" + "	  projects: [ \r\n"
					+ "		{ \r\n" + "			projectName: \"\",\r\n" + "			projectSkills: [],\r\n"
					+ "			projectRole \"\",\r\n" + "			projectDescription:\"\";\r\n"
					+ "			isProjectDeleted: false\r\n" + "		} \r\n" + "	  ] \r\n" + "	} \r\n"
					+ "  ], \r\n" + "  qualification: [ \r\n" + "	{ \r\n" + "	  id: \"\", \r\n"
					+ "	  instutionName: \"\", \r\n" + "	  department: \"\", \r\n"
					+ "	  qualificationStartYear: \"\", \r\n" + "	  qualificationEndYear: \"\", \r\n"
					+ "	  percentage: \"\", \r\n" + "	  isDeleted: false, \r\n" + "	  fieldOfStudy: \"\" \r\n"
					+ "	} \r\n" + "  ], \r\n" + "  certificates: [ \r\n" + "	{ \r\n" + "	  id: \"\", \r\n"
					+ "	  courseName: \"\", \r\n" + "	  courseStartDate: \"\", \r\n" + "	  courseEndDate: \"\", \r\n"
					+ "	  isDeleted: \"\" \r\n" + "	} \r\n" + "  ], \r\n" + "  achievements: [ \r\n" + "	{ \r\n"
					+ "	  id: \"\", \r\n" + "	  achievementsName: \"\", \r\n" + "	  achievementsDate: \"\", \r\n"
					+ "	  isDeleted: false \r\n" + "	} \r\n" + "  ], \r\n" + "  candidateLogo: \"\", \r\n"
					+ "  softSkills: [], \r\n" + "  coreCompentencies: [], \r\n" + "  score: \"\", \r\n"
					+ "  matches: false, \r\n" + "  collegeProject: [ \r\n" + "	{ \r\n" + "	  id: \"\", \r\n"
					+ "	  collegeProjectName: \"\", \r\n" + "	  collegeProjectSkills: [], \r\n"
					+ "	  collegeProjectDescription: \"\", \r\n" + "	  isDeleted: false \r\n" + "	} \r\n"
					+ "  ] \r\n" + "} ";

			String query = " i have attached my resume content below,can you enhance that resume content and create the summary and career Objective and convert all dates into localdate format "
					+ " and based on the resume conent paste in the above Dto an return it" + "\r\n" + "\r\n";

			ChatCompletionRequest chatRequest = new ChatCompletionRequest("gpt-4o-mini",
					candidateDto + query + message);

			ChatCompleitonResponse response = restTemplate.postForObject("https://api.openai.com/v1/chat/completions",
					chatRequest, ChatCompleitonResponse.class);

			responseCandidateDto = convertResponseString(response.getChoices().get(0).getMessage().getContent(),
					resume);

		} catch (Exception e) {
			logger.debug("Service :: getUploadResumeDetialsFromAi :: Exception " + e.getMessage());
		}
		logger.debug("Service :: getUploadResumeDetialsFromAi :: Exited ");
		return responseCandidateDto;
	}

	private CandidateDto convertResponseString(String response, MultipartFile content) throws Exception {

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

			createCandidate = candidateService.createCandidate(responseCandidateDto);

			responseCandidateDto = null;

		} catch (Exception e) {
			if (count > 0) {
				count--;
				getUploadResumeDetialsFromAi(content);
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
