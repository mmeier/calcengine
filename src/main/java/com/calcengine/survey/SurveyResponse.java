package com.calcengine.survey;

import java.util.Set;

/**
 * A SurveyResponse is the collection of answers provided by a Survey Respondent.
 */
public interface SurveyResponse {

	/**
	 * @return the ID of the survey taken.
	 */
	String getSurveyId();

	/**
	 * @return the ID of the respondent who took the survey
	 */
	String getRespondentId();

	/**
	 * @return The set of QuestionIds in this SurveyReponse
	 */
	Set<String> getQuestionIds();

	/**
	 * Returns the respondents answer to a given question. Answer will be null if the respondent did not answer the
	 * specified question.
	 *
	 * @param questionId ID of the question.
	 * @return Answer to the question as a nullable String
	 */
	String getQuestionResponse(String questionId);

}
