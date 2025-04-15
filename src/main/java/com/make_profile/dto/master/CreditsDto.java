package com.make_profile.dto.master;

public class CreditsDto {

	private Long id;

	private Double creditUsed;

	private Double creditAvailable;

	private Long candidateId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getCreditUsed() {
		return creditUsed;
	}

	public void setCreditUsed(Double creditUsed) {
		this.creditUsed = creditUsed;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public Double getCreditAvailable() {
		return creditAvailable;
	}

	public void setCreditAvailable(Double creditAvailable) {
		this.creditAvailable = creditAvailable;
	}

	public Long getCandidateId() {
		return candidateId;
	}

}
