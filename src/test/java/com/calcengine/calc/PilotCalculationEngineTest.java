package com.calcengine.calc;

import com.calcengine.profile.Attribute;
import com.calcengine.profile.Profile;
import com.calcengine.survey.BasicSurveyResponse;
import com.calcengine.survey.SurveyResponse;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class PilotCalculationEngineTest {

	@Test
	public void insufficientDataThrowsException() {
		BasicSurveyResponse response = new BasicSurveyResponse("Test", "Test");
		response.addQuestionResponse("1", "1");
		response.addQuestionResponse("2", "2");
		response.addQuestionResponse("3", "3");
		response.addQuestionResponse("4", "4");
		response.addQuestionResponse("5", "5");
		response.addQuestionResponse("6", "1");
		response.addQuestionResponse("7", "2");
		response.addQuestionResponse("8", null);
		response.addQuestionResponse("9", null);
		response.addQuestionResponse("10", null);

		PilotCalculationEngine calculationEngine = new PilotCalculationEngine();

		assertThatExceptionOfType(CalculationException.class)
				.isThrownBy(() -> calculationEngine.calculate(response))
				.withMessageStartingWith("Insufficient Data.")
				.withMessageContaining("only 70% have been answered.");
	}

	@Test
	public void nonLikertQuestionsAreRejected() {
		BasicSurveyResponse response = new BasicSurveyResponse("Test", "Test");
		response.addQuestionResponse("1", "1");
		response.addQuestionResponse("2", "7"); // Out of range
		response.addQuestionResponse("3", "3");
		response.addQuestionResponse("4", "A"); // Alpha characters aren't Likert
		response.addQuestionResponse("5", "5");
		response.addQuestionResponse("6", "1");
		response.addQuestionResponse("7", "2");
		response.addQuestionResponse("8", "1");
		response.addQuestionResponse("9", "1");
		response.addQuestionResponse("10", "1");

		PilotCalculationEngine calculationEngine = new PilotCalculationEngine();

		assertThatExceptionOfType(CalculationException.class)
				.isThrownBy(() -> calculationEngine.calculate(response))
				.withMessageStartingWith("Invalid Data.")
				.withMessageContaining("Non-Likert");
	}

	@Test
	public void testAccuracyTop() throws CalculationException {
		SurveyResponse surveyResponse = buildResponse(1, 1, 1, 1, 1, 1, 1, 1, 1, 1);

		PilotCalculationEngine calculationEngine = new PilotCalculationEngine();

		Profile profile = calculationEngine.calculate(surveyResponse);

		try (AutoCloseableSoftAssertions softly = new AutoCloseableSoftAssertions()) {
			softly.assertThat(profile.getAttribute(1)).isEqualTo(Attribute.ACCURACY);
			softly.assertThat(profile.getAttribute(2)).isEqualTo(Attribute.FOCUS);
			softly.assertThat(profile.getAttribute(3)).isEqualTo(Attribute.DRIVE);
			softly.assertThat(profile.getAttribute(4)).isEqualTo(Attribute.DISCIPLINE);

			softly.assertThat(profile.getAttributeScore(Attribute.ACCURACY)).isEqualTo(80);
			softly.assertThat(profile.getAttributeScore(Attribute.DISCIPLINE)).isEqualTo(33);
			softly.assertThat(profile.getAttributeScore(Attribute.DRIVE)).isEqualTo(50);
			softly.assertThat(profile.getAttributeScore(Attribute.FOCUS)).isEqualTo(60);
		}
	}

	@Test
	public void testDisciplineTop() throws CalculationException {
		SurveyResponse surveyResponse = buildResponse(1, 2, 3, 4, 5, 1, 2, 3, 4, 5);

		PilotCalculationEngine calculationEngine = new PilotCalculationEngine();

		Profile profile = calculationEngine.calculate(surveyResponse);

		try (AutoCloseableSoftAssertions softly = new AutoCloseableSoftAssertions()) {
			softly.assertThat(profile.getAttribute(1)).isEqualTo(Attribute.DISCIPLINE);
			softly.assertThat(profile.getAttribute(2)).isEqualTo(Attribute.FOCUS);
			softly.assertThat(profile.getAttribute(3)).isEqualTo(Attribute.DRIVE);
			softly.assertThat(profile.getAttribute(4)).isEqualTo(Attribute.ACCURACY);

			softly.assertThat(profile.getAttributeScore(Attribute.ACCURACY)).isEqualTo(30);
			softly.assertThat(profile.getAttributeScore(Attribute.DISCIPLINE)).isEqualTo(67);
			softly.assertThat(profile.getAttributeScore(Attribute.DRIVE)).isEqualTo(44);
			softly.assertThat(profile.getAttributeScore(Attribute.FOCUS)).isEqualTo(50);
		}
	}

	private SurveyResponse buildResponse(int ... responses) {
		BasicSurveyResponse response = new BasicSurveyResponse("Test", "Test");

		for (int i = 1; i < responses.length; i++) {
			response.addQuestionResponse("Q" + String.valueOf(i), String.valueOf(responses[i]));
		}

		return response;
	}
}