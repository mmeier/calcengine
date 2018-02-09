package com.calcengine.calc;

import com.calcengine.profile.Profile;
import com.calcengine.survey.SurveyResponse;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculationEngineTest {

	@Test
	public void hasVersion() {
		CalculationEngine engine = new TestCalculationEngine("Foo", "Bar");

		assertThat(engine.getVersion()).isEqualTo("Foo");
	}

	@Test
	public void hasSupportedSurveyId() {
		CalculationEngine engine = new TestCalculationEngine("Foo", "Bar");

		assertThat(engine.getSupportedSurveyIds()).containsExactlyInAnyOrder("Bar");
	}

	@Test
	public void supportsMultipleSurveyIds() {
		CalculationEngine engine = new TestCalculationEngine("Foo", "Bar", "123", "ABC");

		assertThat(engine.getSupportedSurveyIds()).containsExactlyInAnyOrder("Bar", "ABC", "123");
	}

	private static class TestCalculationEngine extends CalculationEngine {

		public TestCalculationEngine(String version, String... supportedSurveyIds) {
			super(version, supportedSurveyIds);
		}

		@Override
		public Profile calculate(SurveyResponse surveyResponse) throws CalculationException {
			return Profile.blankProfile("test");
		}
	}
}