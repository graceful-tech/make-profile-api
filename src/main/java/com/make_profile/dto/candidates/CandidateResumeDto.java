package com.make_profile.dto.candidates;

import org.springframework.web.multipart.MultipartFile;

public class CandidateResumeDto {

	private Long id;

	private String username;

	private MultipartFile resume;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public MultipartFile getResume() {
		return resume;
	}

	public void setResume(MultipartFile resume) {
		this.resume = resume;
	}

}
