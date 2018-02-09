package com.calcengine.calc.constraint;

import com.calcengine.survey.SurveyResponse;

/**
 * A constraint on SurveyResponses.
 */
public interface Constraint {

	/**
	 * Checks the provided SurveyResponse to see if it violates the constraint.
	 *
	 * @param surveyResponse SurveyResponse to check.
	 * @return true if the constraint if violated.
	 */
	boolean violated(SurveyResponse surveyResponse);
}
