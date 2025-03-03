package com.make_profile.dto.candidates;

import com.make_profile.dto.BaseDto;

import java.time.LocalDate;
import java.util.List;

public class CandidateDto extends BaseDto {

    private Long id;

    private String name;

    private String mobileNumber;

    private String alternateMobileNumber;

    private String email;

    private String nationality;

    private String gender;

    private List<String> languagesKnown;

    private Boolean isFresher;

    private List<String> skills;

    private String linkedIn;

    private String dob;

    private List<CandidateExperienceDto> experiences;

    private List<CandidateQualificationDto> qualification;

    private List<CandidateCertificatesDto> certificates;

    private List<CandidateAchievementsDto> achievements;


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

    public List<String> getLanguagesKnown() {
        return languagesKnown;
    }

    public void setLanguagesKnown(List<String> languagesKnown) {
        this.languagesKnown = languagesKnown;
    }

    public Boolean getFresher() {
        return isFresher;
    }

    public void setFresher(Boolean fresher) {
        isFresher = fresher;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }


    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public List<CandidateExperienceDto> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<CandidateExperienceDto> experiences) {
        this.experiences = experiences;
    }

    public List<CandidateQualificationDto> getQualification() {
        return qualification;
    }

    public void setQualification(List<CandidateQualificationDto> qualification) {
        this.qualification = qualification;
    }

    public List<CandidateCertificatesDto> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<CandidateCertificatesDto> certificates) {
        this.certificates = certificates;
    }

    public List<CandidateAchievementsDto> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<CandidateAchievementsDto> achievements) {
        this.achievements = achievements;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }
}
