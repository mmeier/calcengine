package com.calcengine.survey;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class BasicSurveyResponseTest {

	private static SurveyResponse surveyResponse;

	@BeforeClass
	public static void setupFixture() {
		BasicSurveyResponse basicSurveyResponse = new BasicSurveyResponse("Foo", "Bar");
		basicSurveyResponse.addQuestionResponse("Alpha", "1");
		basicSurveyResponse.addQuestionResponse("Beta", "2");
		basicSurveyResponse.addQuestionResponse("Charlie", "3");

		surveyResponse = basicSurveyResponse;
	}

	@Test
	public void surveyIdCannotBeNull() {
		assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> new BasicSurveyResponse(null, "someone@example.com"));
	}

	@Test
	public void respondentIdCannotBeNull() {
		assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> new BasicSurveyResponse("ABC123", null));
	}

	@Test
	public void hasSurveyIdAndRespondentId() {
		assertThat(surveyResponse.getSurveyId()).isEqualTo("Foo");
		assertThat(surveyResponse.getRespondentId()).isEqualTo("Bar");
	}

	@Test
	public void AllQuestionIdsReturned() {
		assertThat(surveyResponse.getQuestionIds()).containsExactlyInAnyOrder("Alpha", "Beta", "Charlie");
	}

	@Test
	public void allQuestionResponsesReturned() {
		assertThat(surveyResponse.getQuestionResponse("Alpha")).isEqualTo("1");
		assertThat(surveyResponse.getQuestionResponse("Beta")).isEqualTo("2");
		assertThat(surveyResponse.getQuestionResponse("Charlie")).isEqualTo("3");
	}

	@Test
	public void unansweredQuestionReturnsNull() {
		assertThat(surveyResponse.getQuestionResponse("NotAnswered")).isNull();
	}
}