package com.make_profile.dto.candidates;

public class CandidateProjectDetailsDto {

    private Long id;

    private String projectName;

    private String projectSkills;

    private String projectRole;

    private String projectDescription;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectSkills() {
        return projectSkills;
    }

    public void setProjectSkills(String projectSkills) {
        this.projectSkills = projectSkills;
    }

    public String getProjectRole() {
        return projectRole;
    }

    public void setProjectRole(String projectRole) {
        this.projectRole = projectRole;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    @Override
    public String toString() {
        return  "{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", projectSkills='" + projectSkills + '\'' +
                ", projectRole='" + projectRole + '\'' +
                ", projectDescription='" + projectDescription + '\'' +
                '}';
    }
}
