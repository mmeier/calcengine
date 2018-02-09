package com.calcengine.calc.constraint;

import com.calcengine.survey.BasicSurveyResponse;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PercentageConstraintTest {

	@Test
	public void lowerThanRequiredViolates() {
		PercentageConstraint constraint = new PercentageConstraint(50);

		BasicSurveyResponse response = new BasicSurveyResponse("Test", "Test");
		response.addQuestionResponse("Q123", "5");
		response.addQuestionResponse("Q456", null);
		response.addQuestionResponse("Q789", null);

		assertThat(constraint.violated(response)).isTrue();
	}

	@Test
	public void higherThanDoesNotViolate() {
		PercentageConstraint constraint = new PercentageConstraint(50);

		BasicSurveyResponse response = new BasicSurveyResponse("Test", "Test");
		response.addQuestionResponse("Q123", "5");
		response.addQuestionResponse("Q456", "4");
		response.addQuestionResponse("Q789", "A");

		assertThat(constraint.violated(response)).isFalse();
	}

	@Test
	public void equalToDoesNotViolate() {
		PercentageConstraint constraint = new PercentageConstraint(50);

		BasicSurveyResponse response = new BasicSurveyResponse("Test", "Test");
		response.addQuestionResponse("Q123", "5");
		response.addQuestionResponse("Q456", "4");
		response.addQuestionResponse("Q789", null);
		response.addQuestionResponse("Q111", null);

		assertThat(constraint.violated(response)).isFalse();
	}

}