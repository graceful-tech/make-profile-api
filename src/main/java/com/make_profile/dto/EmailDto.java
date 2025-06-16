package com.make_profile.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class EmailDto {
	
	private String fromAddress;

	private List<String> toAddressList;

	private List<String> ccList;

	private List<String> bccList;

	private String subject;

	private String message;

	private List<MultipartFile> attachments;

	private Long requirementId;

	private List<Long> appliedJobIdList;

	private List<Long> jobAttachmentIdList;

	private Long exportTemplateId;

	private Long appliedJobId;

	private Long emailTemplateId;

	private Long interviewId;

	private Long emailConfigId;
	
	private String type;

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public List<String> getToAddressList() {
		return toAddressList;
	}

	public void setToAddressList(List<String> toAddressList) {
		this.toAddressList = toAddressList;
	}

	public List<String> getCcList() {
		return ccList;
	}

	public void setCcList(List<String> ccList) {
		this.ccList = ccList;
	}

	public List<String> getBccList() {
		return bccList;
	}

	public void setBccList(List<String> bccList) {
		this.bccList = bccList;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<MultipartFile> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<MultipartFile> attachments) {
		this.attachments = attachments;
	}

	public Long getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Long requirementId) {
		this.requirementId = requirementId;
	}

	public List<Long> getAppliedJobIdList() {
		return appliedJobIdList;
	}

	public void setAppliedJobIdList(List<Long> appliedJobIdList) {
		this.appliedJobIdList = appliedJobIdList;
	}

	public List<Long> getJobAttachmentIdList() {
		return jobAttachmentIdList;
	}

	public void setJobAttachmentIdList(List<Long> jobAttachmentIdList) {
		this.jobAttachmentIdList = jobAttachmentIdList;
	}

	public Long getExportTemplateId() {
		return exportTemplateId;
	}

	public void setExportTemplateId(Long exportTemplateId) {
		this.exportTemplateId = exportTemplateId;
	}

	public Long getAppliedJobId() {
		return appliedJobId;
	}

	public void setAppliedJobId(Long appliedJobId) {
		this.appliedJobId = appliedJobId;
	}

	public Long getEmailTemplateId() {
		return emailTemplateId;
	}

	public void setEmailTemplateId(Long emailTemplateId) {
		this.emailTemplateId = emailTemplateId;
	}

	public Long getInterviewId() {
		return interviewId;
	}

	public void setInterviewId(Long interviewId) {
		this.interviewId = interviewId;
	}

	public Long getEmailConfigId() {
		return emailConfigId;
	}

	public void setEmailConfigId(Long emailConfigId) {
		this.emailConfigId = emailConfigId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
