package com.make_profile.entity.templates;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="template_applied")
public class TemplateAppliedEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = true, length= 50)
	private String sectionName;
	
	@Column(nullable = true, length= 10)
	private Long sectionId;
	
	@Column(nullable = true)
	private boolean isVisible;
	
	@Column(nullable = true)
	private Long templateId;
	
	@Column(nullable = true)
	private Long candidateId;
	
	@Column(nullable = true) 
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
