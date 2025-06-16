package com.make_profile.entity.history.candidates;

import java.util.List;

import com.make_profile.entity.common.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "candidates_history")
public class CandidateHistoryEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50)
	private String name;

	@Column(nullable = false, length = 20)
	private String mobileNumber;

	@Column(nullable = true, length = 50)
	private String email;

	@Column(nullable = true, length = 20)
	private String nationality;

	@Column(nullable = true, length = 20)
	private String gender;

	@Column(nullable = true)
	private String languagesKnown;

	@Column(nullable = true)
	private boolean isFresher;

	@Column(nullable = true, length = 1000)
	private String skills;

	@Column(nullable = true, length = 50)
	private String linkedIn;

	@Column(nullable = true)
	private String dob;

	@Column(nullable = true, length = 250)
	private String address;

	@Column(nullable = true, length = 20)
	private String maritalStatus;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "candidate_id")
	private List<CandidateExperienceHistoryEntity> experiences;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "candidate_id")
	private List<CandidateAchievementsHistoryEntity> achievements;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "candidate_id")
	private List<CandidateQualificationHistoryEntity> qualification;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "candidate_id")
	private List<CandidateCertificatesHistoryEntity> certificates;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "candidate_id")
	private List<CandidateCollegeProjectHistoryEntity> collegeProject;

	@Column(nullable = true, length = 1000)
	private String softSkills;

	@Column(nullable = true, length = 1000)
	private String coreCompentencies;

	@Column(nullable = true)
	private Long candidateId;

	@Column(nullable = true, length = 10)
	private boolean coreCompentenciesMandatory;

	@Column(nullable = true, length = 10)
	private boolean softSkillsMandatory;

	@Column(nullable = true, length = 10)
	private boolean certificatesMandatory;

	@Column(nullable = true, length = 10)
	private boolean achievementsMandatory;

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

	public boolean isFresher() {
		return isFresher;
	}

	public void setFresher(boolean isFresher) {
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

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
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

	public String getSoftSkills() {
		return softSkills;
	}

	public void setSoftSkills(String softSkills) {
		this.softSkills = softSkills;
	}

	public String getCoreCompentencies() {
		return coreCompentencies;
	}

	public void setCoreCompentencies(String coreCompentencies) {
		this.coreCompentencies = coreCompentencies;
	}

	public List<CandidateExperienceHistoryEntity> getExperiences() {
		return experiences;
	}

	public void setExperiences(List<CandidateExperienceHistoryEntity> experiences) {
		this.experiences = experiences;
	}

	public List<CandidateAchievementsHistoryEntity> getAchievements() {
		return achievements;
	}

	public void setAchievements(List<CandidateAchievementsHistoryEntity> achievements) {
		this.achievements = achievements;
	}

	public List<CandidateQualificationHistoryEntity> getQualification() {
		return qualification;
	}

	public void setQualification(List<CandidateQualificationHistoryEntity> qualification) {
		this.qualification = qualification;
	}

	public List<CandidateCertificatesHistoryEntity> getCertificates() {
		return certificates;
	}

	public void setCertificates(List<CandidateCertificatesHistoryEntity> certificates) {
		this.certificates = certificates;
	}

	public List<CandidateCollegeProjectHistoryEntity> getCollegeProject() {
		return collegeProject;
	}

	public void setCollegeProject(List<CandidateCollegeProjectHistoryEntity> collegeProject) {
		this.collegeProject = collegeProject;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public boolean isCoreCompentenciesMandatory() {
		return coreCompentenciesMandatory;
	}

	public void setCoreCompentenciesMandatory(boolean coreCompentenciesMandatory) {
		this.coreCompentenciesMandatory = coreCompentenciesMandatory;
	}

	public boolean isSoftSkillsMandatory() {
		return softSkillsMandatory;
	}

	public void setSoftSkillsMandatory(boolean softSkillsMandatory) {
		this.softSkillsMandatory = softSkillsMandatory;
	}

	public boolean isCertificatesMandatory() {
		return certificatesMandatory;
	}

	public void setCertificatesMandatory(boolean certificatesMandatory) {
		this.certificatesMandatory = certificatesMandatory;
	}

	public boolean isAchievementsMandatory() {
		return achievementsMandatory;
	}

	public void setAchievementsMandatory(boolean achievementsMandatory) {
		this.achievementsMandatory = achievementsMandatory;
	}

}
