package com.make_profile.service.impl.resume;

import com.make_profile.dto.resume.CommonResumeDto;
import com.make_profile.dto.resume.EducationDto;
import com.make_profile.dto.resume.ExperienceDto;
import com.make_profile.dto.resume.ProjectDto;
import com.make_profile.service.resume.CreateResumeTemplateService;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.collections4.map.HashedMap;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class CreateResumeTemplateTemplateServiceImpl implements CreateResumeTemplateService {

    private static final Logger logger = LoggerFactory.getLogger(CreateResumeTemplateTemplateServiceImpl.class);

    @Autowired
    private Configuration configuration;

    @Override
    public void createResumeTemplate(CommonResumeDto commonResumeDto) {
        logger.debug("Service :: createResumeTemplate :: Extered");

        Map<String, Object> variables = new HashedMap<>();
        Template template = null;
        StringBuilder subject = new StringBuilder();
        try {

            variables.put("phone", commonResumeDto.getPhone());
            variables.put("name", commonResumeDto.getName());
            variables.put("email", commonResumeDto.getEmail());
            variables.put("summary", commonResumeDto.getSummary());

            if (Objects.nonNull(commonResumeDto.getLinkedin()) && !commonResumeDto.getLinkedin().isEmpty()) {
                variables.put("linkedin", commonResumeDto.getLinkedin());
            }

            if (Objects.nonNull(commonResumeDto.getExperiences()) && !commonResumeDto.getExperiences().isEmpty()) {

                List<ExperienceDto> experienceList = new ArrayList<>();

                commonResumeDto.getExperiences().forEach(exp -> {
                    ExperienceDto experiences = new ExperienceDto();
                    experiences.setRole(exp.getRole());
                    experiences.setCompany(exp.getCompany());
                    experiences.setDuration(exp.getDuration());
                    experiences.setResponsibilities(exp.getResponsibilities());

                    experienceList.add(experiences);
                    experiences = null;
                });
                variables.put("experiences", experienceList);
            }

            if (Objects.nonNull(commonResumeDto.getProjects()) && !commonResumeDto.getProjects().isEmpty()) {

                List<ProjectDto> projectsList = new ArrayList<>();

                commonResumeDto.getProjects().forEach(project -> {
                    ProjectDto pro = new ProjectDto();
                    pro.setName(project.getName());
                    pro.setDetails(project.getDetails());

                    projectsList.add(pro);
                    pro = null;
                });
                variables.put("projects", projectsList);
            }

            variables.put("skills", commonResumeDto.getSkills());

            if (Objects.nonNull(commonResumeDto.getCertifications())
                    && !commonResumeDto.getCertifications().isEmpty()) {
                variables.put("certifications", commonResumeDto.getCertifications());
            }

            if (Objects.nonNull(commonResumeDto.getEducation()) && !commonResumeDto.getEducation().isEmpty()) {
                List<EducationDto> educationList = new ArrayList<>();

                commonResumeDto.getEducation().forEach(edu -> {
                    EducationDto education = new EducationDto();

                    education.setDegree(edu.getDegree());
                    education.setInstitution(edu.getInstitution());
                    education.setYear(edu.getYear());
                    education.setPercentage(edu.getPercentage());

                    educationList.add(education);
                    education = null;
                });
                variables.put("education", educationList);
            }

            if (Objects.nonNull(commonResumeDto.getSoftSkills()) && !commonResumeDto.getSoftSkills().isEmpty()) {
                variables.put("softSkills", commonResumeDto.getSoftSkills());
            }

            if (Objects.nonNull(commonResumeDto.getAwards()) && !commonResumeDto.getAwards().isEmpty()) {
                variables.put("awards", commonResumeDto.getAwards());
            }

            if (Objects.nonNull(commonResumeDto.getDob()) && !commonResumeDto.getAwards().isEmpty()) {
                variables.put("dob", commonResumeDto.getDob());
            }

            if (Objects.nonNull(commonResumeDto.getGender()) && !commonResumeDto.getAwards().isEmpty()) {
                variables.put("gender", commonResumeDto.getGender());
            }

            if (Objects.nonNull(commonResumeDto.getLanguages()) && !commonResumeDto.getAwards().isEmpty()) {
                variables.put("languages", commonResumeDto.getLanguages());
            }

            template = configuration.getTemplate("formal_resume.ftl");

            String processTemplateIntoString = FreeMarkerTemplateUtils.processTemplateIntoString(template, variables);

            convertHtmlToPdf(processTemplateIntoString, commonResumeDto.getName() + ".pdf");

            convertHtmlToDocx(processTemplateIntoString, commonResumeDto.getName() +
                    ".docx");

        } catch (Exception e) {
            logger.debug("Service :: createResumeTemplate :: Exited" + e.getMessage());
        }
        logger.debug("Service :: createResumeTemplate :: Exited");

    }

    public static void convertHtmlToPdf(String html, String outputPath) throws Exception {

        logger.debug("Service :: convertHtmlToPdf :: Extered");
        try {
//			try (OutputStream os = new FileOutputStream(outputPath)) {
//				ITextRenderer renderer = new ITextRenderer();
//
//				SharedContext sharedContext = renderer.getSharedContext();
//				sharedContext.setPrint(true);
//				sharedContext.setInteractive(false);
//				sharedContext.getTextRenderer().setSmoothingThreshold(0);
//
//				// Set the HTML content
//				renderer.setDocumentFromString(html);
//				renderer.layout();
//				renderer.createPDF(os);
//				System.out.println("PDF generated successfully using Flying Saucer at: " + outputPath);
//				os.close();
//			}

            OutputStream os = new FileOutputStream(outputPath);
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode(); // Optional: Speeds up rendering
            builder.withHtmlContent(html, null);
            builder.toStream(os);
            builder.run();
            System.out.println("PDF generated successfully at: " + outputPath);

        }

        catch (Exception e) {
            logger.debug("Service :: convertHtmlToPdf :: Exited" + e.getMessage());
        }
        logger.debug("Service :: convertHtmlToPdf :: Exited");
    }

    public static void convertHtmlToDocx(String html, String outputPath) throws Exception {
        logger.debug("Service :: convertHtmlToDocx :: Extered");
        try {

            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
            XHTMLImporterImpl xhtmlImporter = new XHTMLImporterImpl(wordMLPackage);
            wordMLPackage.getMainDocumentPart().getContent().addAll(xhtmlImporter.convert(html, null));
            wordMLPackage.save(new File(outputPath));

            System.out.println("Word file generated successfully: " + outputPath);
        } catch (Exception e) {
            logger.debug("Service :: convertHtmlToDocx :: Exited" + e.getMessage());
        }
        logger.debug("Service :: convertHtmlToDocx :: Exited");
    }
}
