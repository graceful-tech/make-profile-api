package com.make_profile.dto.candidates;

public class CandidateQualificationDto {

    private Long id;

    private String instutionName;

    private String department;

    private String qualificationStartYear;

    private String qualificationEndYear;

    private Double percentage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstutionName() {
        return instutionName;
    }

    public void setInstutionName(String instutionName) {
        this.instutionName = instutionName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getQualificationStartYear() {
        return qualificationStartYear;
    }

    public void setQualificationStartYear(String qualificationStartYear) {
        this.qualificationStartYear = qualificationStartYear;
    }

    public String getQualificationEndYear() {
        return qualificationEndYear;
    }

    public void setQualificationEndYear(String qualificationEndYear) {
        this.qualificationEndYear = qualificationEndYear;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return  "{" +
                "id=" + id +
                ", instutionName='" + instutionName + '\'' +
                ", department='" + department + '\'' +
                ", qualificationStartYear='" + qualificationStartYear + '\'' +
                ", qualificationEndYear='" + qualificationEndYear + '\'' +
                ", percentage=" + percentage +
                '}';
    }
}
