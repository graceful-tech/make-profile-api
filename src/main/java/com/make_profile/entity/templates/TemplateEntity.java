package com.make_profile.entity.templates;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "templates")
public class TemplateEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true, length = 50)
	private String templateName;

	@Column(nullable = true, length = 20)
	private Long minSectionCount;

	@Column(nullable = true, length = 20)
	private Long maxSectionCount;

	@Column(nullable = true)
	private String mandatorySectionName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Long getMinSectionCount() {
		return minSectionCount;
	}

	public void setMinSectionCount(Long minSectionCount) {
		this.minSectionCount = minSectionCount;
	}

	public Long getMaxSectionCount() {
		return maxSectionCount;
	}

	public void setMaxSectionCount(Long maxSectionCount) {
		this.maxSectionCount = maxSectionCount;
	}

	public String getMandatorySectionName() {
		return mandatorySectionName;
	}

	public void setMandatorySectionName(String mandatorySectionName) {
		this.mandatorySectionName = mandatorySectionName;
	}

}
