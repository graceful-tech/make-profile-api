package com.make_profile.service.candidates;

 import com.make_profile.exception.MakeProfileException;

public interface CheckResumePageCountService {

	String getResumeHtmlCode(String resume, Long candidateId, String username, String templateName) throws MakeProfileException;

}
