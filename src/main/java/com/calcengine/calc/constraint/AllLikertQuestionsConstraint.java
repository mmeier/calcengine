package com.calcengine.calc.constraint;

import com.calcengine.survey.SurveyResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * Ensures all questions contain likert answers. Likert answers are numbers from 1 to 5.
 */
public class AllLikertQuestionsConstraint implements Constraint {

	@Override
	public boolean violated(SurveyResponse surveyResponse) {
		for (String questionId : surveyResponse.getQuestionIds()) {
			String response = surveyResponse.getQuestionResponse(questionId);

			if (response == null) continue; // Null answers are ignored

			if (StringUtils.isNumeric(response)) {
				int value = Integer.parseInt(response);

				if (value < 1 || value > 5) {
					return true; // Value is out of range
				}
			} else  {
				return true; // Value must be numeric
			}
		}

		return false;
	}
}
