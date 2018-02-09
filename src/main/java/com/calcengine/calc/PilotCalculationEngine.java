package com.calcengine.calc;

import com.calcengine.calc.constraint.AllLikertQuestionsConstraint;
import com.calcengine.calc.constraint.PercentageConstraint;
import com.calcengine.profile.Attribute;
import com.calcengine.profile.Profile;
import com.calcengine.survey.SurveyResponse;

import java.util.*;
import java.util.stream.Stream;

/**
 * CalculationEngine for the Pilot Version of the PersonalAttributeSurvey
 */
public class PilotCalculationEngine extends CalculationEngine {

	private static final String VERSION = "PA_PILOT_2015.01.30";
	private static final String[] SUPPORTED_SURVEY_IDS = {"123456"};

	private static final String[] FORWARD_CODED_QUESTIONS = {"Q1", "Q3", "Q9", "Q10"};
	private static final String[] BACKWARD_CODED_QUESTIONS = {"Q2", "Q4", "Q5", "Q6", "Q7", "Q8"};

	private static final String[] ACCURACY_QUESTIONS = {"Q2", "Q4", "Q6", "Q8", "Q10"};
	private static final String[] DISCIPLINE_QUESTIONS = {"Q1", "Q3", "Q5"};
	private static final String[] DRIVE_QUESTIONS = {"Q7", "Q8", "Q9", "Q10"};
	private static final String[] FOCUS_QUESTIONS = {"Q1", "Q2", "Q3", "Q4", "Q5"};

	private Map<String, Double> codedResponses;
	private TreeMap<Integer, Attribute> attributeScores;


	public PilotCalculationEngine() {
		super(VERSION, SUPPORTED_SURVEY_IDS);
	}

	@Override
	public Profile calculate(SurveyResponse surveyResponse) throws CalculationException {
		validate(surveyResponse);
		codeResponses(surveyResponse);

		scoreAttributes();

		return buildProfile(surveyResponse.getRespondentId());
	}

	private void scoreAttributes() {
		attributeScores = new TreeMap<>();

		Stream.of(Attribute.values())
				.forEach(attribute -> attributeScores.put(score(attribute), attribute));
	}

	private int score(Attribute attribute) {
		List<String> questionList = getQuestionList(attribute);

		double sum = questionList.stream()
				.mapToDouble(codedResponses::get)
				.sum();

		double average = (sum / questionList.size()) * 100;

		return (int) Math.round(average);
	}

	private List<String> getQuestionList(Attribute attribute) {
		switch(attribute) {
			case ACCURACY:
				return Arrays.asList(ACCURACY_QUESTIONS);
			case DISCIPLINE:
				return Arrays.asList(DISCIPLINE_QUESTIONS);
			case DRIVE:
				return Arrays.asList(DRIVE_QUESTIONS);
			case FOCUS:
				return Arrays.asList(FOCUS_QUESTIONS);
		}

		throw new IllegalStateException("No question list defined for " + attribute);
	}

	private void codeResponses(SurveyResponse surveyResponse) {
		codedResponses = new HashMap<>();

		for (String questionId : FORWARD_CODED_QUESTIONS) {
			codedResponses.put(questionId, Coding.forward(surveyResponse.getQuestionResponse(questionId)));
		}

		for (String questionId : BACKWARD_CODED_QUESTIONS) {
			codedResponses.put(questionId, Coding.backward(surveyResponse.getQuestionResponse(questionId)));
		}
	}

	private Profile buildProfile(String respondentId) {
		Profile.Builder builder = Profile.builder()
				.withId(respondentId);

		attributeScores.descendingKeySet()
				.forEach(score -> builder.withAttribute(attributeScores.get(score), score));

		return builder.build();
	}

	private void validate(SurveyResponse surveyResponse) throws CalculationException {
		PercentageConstraint percentageConstraint = new PercentageConstraint(80);
		AllLikertQuestionsConstraint allLikertQuestionsConstraint = new AllLikertQuestionsConstraint();

		if (percentageConstraint.violated(surveyResponse)) {
			String message = "Insufficient Data. 80%% of questions must be answered, but only %s%% have been answered.";
			throw new CalculationException(String.format(message, percentageConstraint.getPercentAnswered()));
		}

		if (allLikertQuestionsConstraint.violated(surveyResponse)) {
			throw new CalculationException("Invalid Data. Non-Likert questionResponses have been detected.");
		}
	}
}
