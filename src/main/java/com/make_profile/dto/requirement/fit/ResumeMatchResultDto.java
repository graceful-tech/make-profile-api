package com.make_profile.dto.requirement.fit;

import java.util.List;

public class ResumeMatchResultDto {

	private boolean match;

	private String matchScore;

	private List<RequirementFitDto> requirementMyresumeFit;

	private List<String> improvementSuggestions;

	private String finalVerdict;

	public boolean isMatch() {
		return match;
	}

	public void setMatch(boolean match) {
		this.match = match;
	}

	public String getMatchScore() {
		return matchScore;
	}

	public void setMatchScore(String matchScore) {
		this.matchScore = matchScore;
	}

	public List<RequirementFitDto> getRequirementMyresumeFit() {
		return requirementMyresumeFit;
	}

	public void setRequirementMyresumeFit(List<RequirementFitDto> requirementMyresumeFit) {
		this.requirementMyresumeFit = requirementMyresumeFit;
	}

	public List<String> getImprovementSuggestions() {
		return improvementSuggestions;
	}

	public void setImprovementSuggestions(List<String> improvementSuggestions) {
		this.improvementSuggestions = improvementSuggestions;
	}

	public String getFinalVerdict() {
		return finalVerdict;
	}

	public void setFinalVerdict(String finalVerdict) {
		this.finalVerdict = finalVerdict;
	}

}
