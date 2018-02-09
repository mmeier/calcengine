package com.calcengine.calc.constraint;

import com.calcengine.survey.BasicSurveyResponse;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AllLikertQuestionsConstraintTest {

	private AllLikertQuestionsConstraint constraint;

	@Before
	public void setup() {
		constraint = new AllLikertQuestionsConstraint();
	}

	@Test
	public void allLikertResponsesDoesNotViolate() {
		BasicSurveyResponse response = new BasicSurveyResponse("Test", "Test");
		response.addQuestionResponse("Q1", "1");
		response.addQuestionResponse("Q2", "2");
		response.addQuestionResponse("Q3", "3");
		response.addQuestionResponse("Q4", "4");
		response.addQuestionResponse("Q5", "5");

		assertThat(constraint.violated(response)).isFalse();
	}

	@Test
	public void outOfRangeValuesViolate() {
		BasicSurveyResponse response = new BasicSurveyResponse("Test", "Test");
		response.addQuestionResponse("Q1", "1");
		response.addQuestionResponse("Q2", "6");

		assertThat(constraint.violated(response)).isTrue();
	}

	@Test
	public void alphaResponsesViolate() {
		BasicSurveyResponse response = new BasicSurveyResponse("Test", "Test");
		response.addQuestionResponse("Q1", "A");
		response.addQuestionResponse("Q2", "D");

		assertThat(constraint.violated(response)).isTrue();
	}

	@Test
	public void verbatimResponseViolates() {
		BasicSurveyResponse response = new BasicSurveyResponse("Test", "Test");
		response.addQuestionResponse("Q1", "This is a verbatim response");
		response.addQuestionResponse("Q2", "Another Verbatim Response");

		assertThat(constraint.violated(response)).isTrue();
	}

	@Test
	public void nullValuesDoNotViolate() {
		BasicSurveyResponse response = new BasicSurveyResponse("Test", "Test");
		response.addQuestionResponse("Q1", "1");
		response.addQuestionResponse("Q2", "2");
		response.addQuestionResponse("Q3", null);
		response.addQuestionResponse("Q4", "4");
		response.addQuestionResponse("Q5", "5");

		assertThat(constraint.violated(response)).isFalse();
	}
}