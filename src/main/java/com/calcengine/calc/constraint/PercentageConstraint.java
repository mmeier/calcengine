package com.calcengine.calc.constraint;

import com.calcengine.survey.SurveyResponse;

/**
 * Constraint that requires a SurveyResponse to have a specified percentage of questions have answers.
 */
public class PercentageConstraint {

	private final int requiredPercentage;

	public PercentageConstraint(int requiredPercentage) {
		this.requiredPercentage = requiredPercentage;
	}

	/**
	 * @param surveyResponse SurveyResponse to check
	 * @return true if the SurveyResponse violates the constraint
	 */
	public boolean violated(SurveyResponse surveyResponse) {
		int totalQuestions = 0;
		int answeredQuestions = 0;

		for (String questionId : surveyResponse.getQuestionIds()) {
			totalQuestions++;

			if (surveyResponse.getQuestionResponse(questionId) != null) {
				answeredQuestions++;
			}
		}

		int percentAnswered = computePercentage(answeredQuestions, totalQuestions);
		return percentAnswered < requiredPercentage;
	}

	private int computePercentage(int answeredQuestions, int totalQuestions) {
		float percentage = ((float) answeredQuestions / (float) totalQuestions) * 100;

		return Math.round(percentage);
	}
}