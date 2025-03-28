package com.make_profile.dto.candidates;

import java.time.LocalDate;

import com.make_profile.dto.master.HurecomRequirementsDto;

public class HurecomAppliedJobsDto {

	private Long id;

	private HurecomCandidateDto candidate;

	private String interviewMode;

	private String interviewLocation;

	private LocalDate interviewDate;

	private String interviewTime;

	private LocalDate callBackDate;

	private String callBackTime;

	private LocalDate joiningDate;

	private Double acceptedCostToCompany;

	private String statusCategory;

	private String status;

	private Long optionalCid;

	private String candidatePoolRecord;

	private String sentToQualityAudit;

	private String qualityStatus;

	private String qualityAuditComplete;

	private boolean documentChecked;

	private String score;

	private boolean releated;

	private HurecomRequirementsDto requirement;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public HurecomCandidateDto getCandidate() {
		return candidate;
	}

	public void setCandidate(HurecomCandidateDto candidate) {
		this.candidate = candidate;
	}

	public String getInterviewMode() {
		return interviewMode;
	}

	public void setInterviewMode(String interviewMode) {
		this.interviewMode = interviewMode;
	}

	public String getInterviewLocation() {
		return interviewLocation;
	}

	public void setInterviewLocation(String interviewLocation) {
		this.interviewLocation = interviewLocation;
	}

	public LocalDate getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(LocalDate interviewDate) {
		this.interviewDate = interviewDate;
	}

	public String getInterviewTime() {
		return interviewTime;
	}

	public void setInterviewTime(String interviewTime) {
		this.interviewTime = interviewTime;
	}

	public LocalDate getCallBackDate() {
		return callBackDate;
	}

	public void setCallBackDate(LocalDate callBackDate) {
		this.callBackDate = callBackDate;
	}

	public String getCallBackTime() {
		return callBackTime;
	}

	public void setCallBackTime(String callBackTime) {
		this.callBackTime = callBackTime;
	}

	public LocalDate getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}

	public Double getAcceptedCostToCompany() {
		return acceptedCostToCompany;
	}

	public void setAcceptedCostToCompany(Double acceptedCostToCompany) {
		this.acceptedCostToCompany = acceptedCostToCompany;
	}

	public String getStatusCategory() {
		return statusCategory;
	}

	public void setStatusCategory(String statusCategory) {
		this.statusCategory = statusCategory;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getOptionalCid() {
		return optionalCid;
	}

	public void setOptionalCid(Long optionalCid) {
		this.optionalCid = optionalCid;
	}

	public String getCandidatePoolRecord() {
		return candidatePoolRecord;
	}

	public void setCandidatePoolRecord(String candidatePoolRecord) {
		this.candidatePoolRecord = candidatePoolRecord;
	}

	public String getSentToQualityAudit() {
		return sentToQualityAudit;
	}

	public void setSentToQualityAudit(String sentToQualityAudit) {
		this.sentToQualityAudit = sentToQualityAudit;
	}

	public String getQualityStatus() {
		return qualityStatus;
	}

	public void setQualityStatus(String qualityStatus) {
		this.qualityStatus = qualityStatus;
	}

	public String getQualityAuditComplete() {
		return qualityAuditComplete;
	}

	public void setQualityAuditComplete(String qualityAuditComplete) {
		this.qualityAuditComplete = qualityAuditComplete;
	}

	public boolean isDocumentChecked() {
		return documentChecked;
	}

	public void setDocumentChecked(boolean documentChecked) {
		this.documentChecked = documentChecked;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public boolean isReleated() {
		return releated;
	}

	public void setReleated(boolean releated) {
		this.releated = releated;
	}

	public HurecomRequirementsDto getRequirement() {
		return requirement;
	}

	public void setRequirement(HurecomRequirementsDto requirement) {
		this.requirement = requirement;
	}

}
