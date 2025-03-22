package com.make_profile.entity.candidates;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "candidate_achievements")
public class CandidateAchievementsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true, length = 50)
	private String achievementsName;

	@Column(nullable = true)
	private LocalDate achievementsDate;

	@ManyToOne
	@JoinColumn(name = "candidate_id")
	private CandidateEntity candidateId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAchievementsName() {
		return achievementsName;
	}

	public void setAchievementsName(String achievementsName) {
		this.achievementsName = achievementsName;
	}

	public CandidateEntity getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(CandidateEntity candidateId) {
		this.candidateId = candidateId;
	}

	public LocalDate getAchievementsDate() {
		return achievementsDate;
	}

	public void setAchievementsDate(LocalDate achievementsDate) {
		this.achievementsDate = achievementsDate;
	}

}
