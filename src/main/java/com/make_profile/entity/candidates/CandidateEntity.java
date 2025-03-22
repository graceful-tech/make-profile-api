package com.make_profile.entity.candidates;

import java.time.LocalDate;
import java.util.List;

import com.make_profile.entity.common.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "candidates")
public class CandidateEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50)
	private String name;

	@Column(nullable = false, length = 20)
	private String mobileNumber;

	@Column(nullable = true, length = 20)
	private String alternateMobileNumber;

	@Column(nullable = true, length = 50)
	private String email;

	@Column(nullable = true, length = 20)
	private String nationality;

	@Column(nullable = true, length = 20)
	private String gender;

	@Column(nullable = true)
	private String languagesKnown;

	@Column(nullable = true)
	private Boolean isFresher;

	@Column(nullable = true, length = 1000)
	private String skills;

	@Column(nullable = true, length = 50)
	private String linkedIn;

	@Column
	private LocalDate dob;

	@Column(nullable = true, length = 250)
	private String address;

	@Column(nullable = true, length = 20)
	private String maritalStatus;

	@OneToMany(mappedBy = "candidateId", cascade = CascadeType.ALL)
	private List<CandidateExperienceEntity> experiences;

	@OneToMany(mappedBy = "candidateId", cascade = CascadeType.ALL)
	private List<CandidateAchievementsEntity> achievements;

	@OneToMany(mappedBy = "candidateId", cascade = CascadeType.ALL)
	private List<CandidateQualificationEntity> qualification;

	@OneToMany(mappedBy = "candidateId", cascade = CascadeType.ALL)
	private List<CandidateCertificateEntity> certificates;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAlternateMobileNumber() {
		return alternateMobileNumber;
	}

	public void setAlternateMobileNumber(String alternateMobileNumber) {
		this.alternateMobileNumber = alternateMobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLanguagesKnown() {
		return languagesKnown;
	}

	public void setLanguagesKnown(String languagesKnown) {
		this.languagesKnown = languagesKnown;
	}

	public Boolean getIsFresher() {
		return isFresher;
	}

	public void setIsFresher(Boolean isFresher) {
		this.isFresher = isFresher;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getLinkedIn() {
		return linkedIn;
	}

	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public List<CandidateExperienceEntity> getExperiences() {
		return experiences;
	}

	public void setExperiences(List<CandidateExperienceEntity> experiences) {
		this.experiences = experiences;
	}

	public List<CandidateAchievementsEntity> getAchievements() {
		return achievements;
	}

	public void setAchievements(List<CandidateAchievementsEntity> achievements) {
		this.achievements = achievements;
	}

	public List<CandidateQualificationEntity> getQualification() {
		return qualification;
	}

	public void setQualification(List<CandidateQualificationEntity> qualification) {
		this.qualification = qualification;
	}

	public List<CandidateCertificateEntity> getCertificates() {
		return certificates;
	}

	public void setCertificates(List<CandidateCertificateEntity> certificates) {
		this.certificates = certificates;
	}

}
