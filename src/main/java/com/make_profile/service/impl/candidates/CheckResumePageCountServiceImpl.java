package com.make_profile.service.impl.candidates;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.make_profile.dto.templates.TemplatePagesDto;
import com.make_profile.entity.candidates.CandidateEntity;
import com.make_profile.entity.master.CreditsEntity;
import com.make_profile.exception.MakeProfileException;
import com.make_profile.repository.candidates.CandidatesRepository;
import com.make_profile.repository.master.CreditsRepository;
import com.make_profile.repository.user.UserRepository;
import com.make_profile.service.candidates.CheckResumePageCountService;
import com.make_profile.utility.CommonConstants;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CheckResumePageCountServiceImpl implements CheckResumePageCountService {

	private static final Logger logger = LoggerFactory.getLogger(CheckResumePageCountServiceImpl.class);

	private static final double MIN_LINE_HEIGHT = 1.0;

	@Autowired
	CandidatesRepository candidatesRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CreditsRepository creditsRepository;

	@Autowired
	ModelMapper mapper;

	@Override
	public String getResumeHtmlCode(String resume, Long candidateId, String username, String templateName)
			throws MakeProfileException {
		logger.debug("Service :: getResumeHtmlCode :: Entered");

		String addSpaceString = null;
		String removeSpaceString = null;
		String heightDecrement = null;
		String heightIncreament = null;
		String removeSectionByTitle = null;
		int pdfPageCount = 0;
		int reCheckPageCount = 0;

		List<String> nonMandatoryFields = new ArrayList<>();
		CandidateEntity candidateEntity = null;
		CreditsEntity creditsEntity = null;

		Long userId = null;

		try {

			if (getPageSize(templateName).equals("1")) {
				pdfPageCount = getPdfPageCount(resume);

				if (pdfPageCount == 1) {
					addSpaceString = addOnePageSpace(resume);

					reCheckPageCount = getPdfPageCount(addSpaceString);

					if (reCheckPageCount == 2) {
						removeSpaceString = removeOnePageSpace(addSpaceString);

						addSpaceString = null;
						removeSectionByTitle = null;
						heightDecrement = null;
						heightIncreament = null;

						return removeSpaceString;
					} else if (reCheckPageCount == 1) {

						double lineHeight = 0;
						for (int i = 1; i < 6; i++) {
							lineHeight = lineHeight + 0.1;
							heightDecrement = adjustLineHeight(resume, lineHeight);
							heightIncreament = adjustLineHeight(resume, Double.valueOf("0.") + lineHeight);
							reCheckPageCount = getPdfPageCount(heightIncreament);

							if (reCheckPageCount == 1) {
								addSpaceString = addOnePageSpace(heightDecrement);
								reCheckPageCount = getPdfPageCount(addSpaceString);

								if (reCheckPageCount == 2) {
									removeSpaceString = removeOnePageSpace(addSpaceString);
									return removeSpaceString;
								}
							}
						}

						userId = userRepository.getUserId(username);
						creditsEntity = creditsRepository.findCreditsByUserIdAndTemplateName(userId, templateName);

						creditsEntity.setCreditAvailable(creditsEntity.getCreditAvailable() + 1);
						creditsEntity.setCreditUsed(creditsEntity.getCreditUsed() - 1);

						creditsRepository.save(creditsEntity);

						throw new MakeProfileException(CommonConstants.MP_0008);

					}
				}

				else if (pdfPageCount == 2) {

					candidateEntity = candidatesRepository.findById(candidateId).get();

					if (!candidateEntity.isAchievementsMandatory()) {
						nonMandatoryFields.add("Achievements");
					}
					if (!candidateEntity.isCertificatesMandatory()) {
						nonMandatoryFields.add("Certificates");
					}
					if (!candidateEntity.isSoftSkillsMandatory()) {
						nonMandatoryFields.add("Soft skills");
					}
					if (!candidateEntity.isCoreCompentenciesMandatory()) {
						nonMandatoryFields.add("Core competencies");
					}

					if (Objects.nonNull(nonMandatoryFields) && !CollectionUtils.isEmpty(nonMandatoryFields)) {

						for (String fields : nonMandatoryFields) {

							removeSectionByTitle = removeSectionByTitle(resume, fields);

							resume = removeSectionByTitle;

							reCheckPageCount = getPdfPageCount(removeSectionByTitle);

							if (reCheckPageCount == 1) {

								addSpaceString = null;
								removeSpaceString = null;
								heightDecrement = null;
								heightIncreament = null;

								return removeSectionByTitle;
							}
						}

						userId = userRepository.getUserId(username);
						creditsEntity = creditsRepository.findCreditsByUserIdAndTemplateName(userId, templateName);

						creditsEntity.setCreditAvailable(creditsEntity.getCreditAvailable() + 1);
						creditsEntity.setCreditUsed(creditsEntity.getCreditUsed() - 1);

						creditsRepository.save(creditsEntity);

						creditsEntity = null;
						userId = null;
						candidateEntity = null;
						addSpaceString = null;
						removeSpaceString = null;
						heightDecrement = null;
						heightIncreament = null;
						removeSpaceString = null;

						throw new MakeProfileException(CommonConstants.MP_0009);
					}
				}

			} else {

				return checkTwoPageResume(resume, candidateId, username, templateName);
			}
		} catch (MakeProfileException e) {
			logger.error("Service :: getResumeHtmlCode :: MakeProfileException :: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("Service :: getResumeHtmlCode :: Exception" + e.getMessage());
		}
		logger.debug("Service :: getResumeHtmlCode :: Entered");
		return null;
	}

	public static int getPdfPageCount(String html) throws Exception {
		logger.debug("Service :: getPdfPageCount :: Entered");

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

			PdfRendererBuilder builder = new PdfRendererBuilder();

			builder.useDefaultPageSize(210, 297, PdfRendererBuilder.PageSizeUnits.MM);
			builder.withHtmlContent(html, new File(".").toURI().toString());
			builder.toStream(baos);
			builder.run();

			logger.debug("Service :: getPdfPageCount :: Exited");

			try (PDDocument document = PDDocument.load(baos.toByteArray())) {
				return document.getNumberOfPages();
			}
		} catch (Exception e) {
			logger.error("Service :: getPdfPageCount :: Exception" + e.getMessage());
			return 0;
		}
	}

	public static String adjustLineHeight(String html, double increment) {
		logger.debug("Service :: adjustLineHeight :: Entered");

		try {
			Pattern pattern = Pattern.compile("line-height\\s*:\\s*([0-9]*\\.?[0-9]+)");
			Matcher matcher = pattern.matcher(html);

			StringBuffer result = new StringBuffer();

			while (matcher.find()) {
				double original = Double.parseDouble(matcher.group(1));

				if (2.2 > original && 1.0 < original) {

					double updated = original + increment;

					if (updated > MIN_LINE_HEIGHT) {
						// updated = MIN_LINE_HEIGHT;
						matcher.appendReplacement(result, "line-height: " + String.format("%.2f", updated));

					}
				}
			}
			matcher.appendTail(result);

			logger.debug("Service :: adjustLineHeight :: Exited");

			return result.toString();
		} catch (Exception e) {
			logger.error("Service :: adjustLineHeight :: Exception" + e.getMessage());
			return null;
		}
	}

	public String addSpace(String resume) {
		logger.debug("Service :: addSpace :: Entered");

		try {
			resume = resume.replace("</body>", "<div style=\"height: 60.1mm;\"></div></body>");
		} catch (Exception e) {
			logger.error("Service :: addSpace :: Exception" + e.getMessage());
		}
		logger.debug("Service :: addSpace :: Exited");
		return resume;
	}

	public String removeSpace(String resume) {
		logger.debug("Service :: removeSpace :: Entered");

		try {
			resume = resume.replace("<div style=\"height: 60.1mm;\"></div>", "");
		} catch (Exception e) {
			logger.error("Service :: removeSpace :: Exception" + e.getMessage());
		}
		logger.debug("Service :: removeSpace :: Exited");
		return resume;
	}

	public String removeSectionByTitle(String html, String sectionTitle) {
		logger.debug("Service :: removeSectionByTitle :: Entered");

		String modifiedHtml = null;
		try {
			Document doc = Jsoup.parse(html);
			Elements sections = doc.select("div.section");
			for (Element section : sections) {
				Element title = section.selectFirst("div.section-title");

				if (title != null && title.text().trim().equalsIgnoreCase(sectionTitle)) {
					section.remove();
					break;
				}
			}
			modifiedHtml = doc.outerHtml();
			modifiedHtml = modifiedHtml.replace("<!doctype html>", "<!DOCTYPE html>\n");
			modifiedHtml = modifiedHtml.replace("<meta charset=\"UTF-8\">", "<meta charset=\"UTF-8\" />");
			modifiedHtml = modifiedHtml.replace("class=\"profile-pic\">", "class=\"profile-pic\" />");

		} catch (Exception e) {
			logger.error("Service :: removeSectionByTitle :: Exception" + e.getMessage());
		}
		logger.debug("Service :: removeSectionByTitle :: Exited");
		return modifiedHtml;
	}

	private String getPageSize(String templateName) {
		logger.debug("Service :: getPageSize :: Entered");

		ObjectMapper objectMapper = new ObjectMapper();
		String pages = null;
		try {
			InputStream is = getClass().getClassLoader().getResourceAsStream("resume_pages/template.json");

			if (is == null) {
				throw new IllegalArgumentException("Template file not found in resources.");
			}
			List<TemplatePagesDto> templates;
			templates = objectMapper.readValue(is, new TypeReference<List<TemplatePagesDto>>() {
			});
			pages = templates.stream().filter(t -> t.getTemplateName().equalsIgnoreCase(templateName)).findFirst().get()
					.getPages();
			objectMapper = null;

		} catch (Exception e) {
			logger.error("Service :: getPageSize :: Exception" + e.getMessage());
		}
		logger.debug("Service :: getPageSize :: Exited");
		return pages;

	}

	@SuppressWarnings("unused")
	private String checkTwoPageResume(String resume, Long candidateId, String username, String templateName)
			throws MakeProfileException {
		logger.debug("Service :: checkTwoPageResume :: Entered");

		String addSpaceString = null;
		String removeSpaceString = null;
		String heightDecrement = null;
		String heightIncreament = null;
		String removeSectionByTitle = null;
		int pdfPageCount = 0;
		int reCheckPageCount = 0;

		CreditsEntity creditsEntity = null;
		Long userId = null;

		try {
			pdfPageCount = getPdfPageCount(resume);
			addSpaceString = addSpace(resume);
			reCheckPageCount = getPdfPageCount(addSpaceString);

			if (reCheckPageCount == 2) {
				removeSpaceString = removeSpace(addSpaceString);

				addSpaceString = null;
				removeSectionByTitle = null;
				heightDecrement = null;
				heightIncreament = null;

				return removeSpaceString;
			} else if (reCheckPageCount == 1) {

				double lineHeight = 0;
				for (int i = 1; i < 6; i++) {
					lineHeight = lineHeight + 0.1;
					heightDecrement = adjustLineHeight(resume, lineHeight);
					heightIncreament = adjustLineHeight(resume, Double.valueOf("0.") + lineHeight);
					reCheckPageCount = getPdfPageCount(heightIncreament);

					if (reCheckPageCount == 1) {
						addSpaceString = addSpace(heightDecrement);
						reCheckPageCount = getPdfPageCount(addSpaceString);

						if (reCheckPageCount == 2) {
							removeSpaceString = removeSpace(addSpaceString);
							return removeSpaceString;
						}
					}
				}
				userId = userRepository.getUserId(username);
				creditsEntity = creditsRepository.findCreditsByUserIdAndTemplateName(userId, templateName);

				creditsEntity.setCreditAvailable(creditsEntity.getCreditAvailable() + 1);
				creditsEntity.setCreditUsed(creditsEntity.getCreditUsed() - 1);

				creditsRepository.save(creditsEntity);

				throw new MakeProfileException(CommonConstants.MP_0008);
			}
		} catch (MakeProfileException e) {
			logger.error("Service :: checkTwoPageResume :: MakeProfileException :: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("Service :: checkTwoPageResume :: Exception" + e.getMessage());
		}
		logger.debug("Service :: checkTwoPageResume :: Exited");
		return null;

	}
	
	public String addOnePageSpace(String resume) {
	    logger.debug("Service :: addOnePageSpace :: Entered");

	    try {
	        int lastDivIndex = resume.lastIndexOf("</div>");
	        int bodyIndex = resume.indexOf("</body>");
	        
 	        if (lastDivIndex != -1 && lastDivIndex < bodyIndex) {
	            String before = resume.substring(0, lastDivIndex);
	            String after = resume.substring(lastDivIndex);
	            resume = before + "<div style=\"height: 60.1mm;\"></div>" + after;
	        }
	    } catch (Exception e) {
	        logger.error("Service :: addOnePageSpace :: Exception" + e.getMessage());
	    }
	    logger.debug("Service :: addOnePageSpace :: Exited");
	    return resume;
	}

	
	public String removeOnePageSpace(String resume) {
	    logger.debug("Service :: removeOnePageSpace :: Entered");
	    
	    try {
	        resume = resume.replace("<div style=\"height: 60.1mm;\"></div>", "");
	    } catch (Exception e) {
	        logger.error("Service :: removeOnePageSpace :: Exception" + e.getMessage());
	    }
	    logger.debug("Service :: removeOnePageSpace :: Exited");
	    return resume;
	}


}
