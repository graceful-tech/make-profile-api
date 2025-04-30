package com.make_profile.dto.master;

import java.time.LocalDate;

public class CreditsDto {

	private Long id;

	private Double creditUsed;

	private Double creditAvailable;

	private Long candidateId;

	private Long userId;

	private LocalDate PaymentDate;

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public LocalDate getPaymentDate() {
		return PaymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		PaymentDate = paymentDate;
	}

}
