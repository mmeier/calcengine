package com.calcengine.survey;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Basic implementation of the SurveyResponse interface. When in doubt, this is a good general-purpose SurveyResponse.
 */
public class BasicSurveyResponse implements SurveyResponse {
	private final String surveyId;
	private final String respondentId;
	private final Map<String, String> questionResponses;

	public BasicSurveyResponse(String surveyId, String respondentId) {
		validate(surveyId, respondentId);
		this.surveyId = surveyId;
		this.respondentId = respondentId;
		questionResponses = new HashMap<>();
	}

	@Override
	public String getSurveyId() {
		return surveyId;
	}

	@Override
	public String getRespondentId() {
		return respondentId;
	}

	@Override
	public Set<String> getQuestionIds() {
		return questionResponses.keySet();
	}

	@Override
	public String getQuestionResponse(String questionId) {
		return questionResponses.get(questionId);
	}

	public void addQuestionResponse(String questionId, String response) {
		if (questionId == null || questionId.trim().isEmpty()) {
			throw new IllegalArgumentException("Question ID cannot be null.");
		}

		questionResponses.put(questionId, response);
	}

	private void validate(String surveyId, String respondentId) {
		if (surveyId == null || surveyId.trim().isEmpty()) {
			throw new IllegalArgumentException("SurveyID cannot be null.");
		}

		if (respondentId == null || respondentId.trim().isEmpty()) {
			throw new IllegalArgumentException("RespondendID cannot be null.");
		}
	}
}
