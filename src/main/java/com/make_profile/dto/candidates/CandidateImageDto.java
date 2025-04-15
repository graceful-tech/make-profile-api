package com.make_profile.dto.candidates;

import org.springframework.web.multipart.MultipartFile;

public class CandidateImageDto {

	private Long id;

	private Long candidateId;

	private MultipartFile attachment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public MultipartFile getAttachment() {
		return attachment;
	}

	public void setAttachment(MultipartFile attachment) {
		this.attachment = attachment;
	}
	
	

}
