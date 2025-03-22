package com.make_profile.dto.templates;

public class TemplateAppliedDto {

	private Long id;

	private String sectionName;

	private Long sectionId;

	private boolean isVisible;

	private Long templateId;

	private Long candidateId;

	private Long usedTemplateId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public Long getUsedTemplateId() {
		return usedTemplateId;
	}

	public void setUsedTemplateId(Long usedTemplateId) {
		this.usedTemplateId = usedTemplateId;
	}

}
