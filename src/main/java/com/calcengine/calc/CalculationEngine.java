package com.calcengine.calc;

import com.calcengine.profile.Profile;
import com.calcengine.survey.SurveyResponse;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Calculation Engines contain the logic that translates raw survey response data into a Profile.
 *
 * CalculationEngines are based on Syntax files, typically SPS code from the researcher's statistical software. The
 * version should reflect the name and publication date of the syntax file the calculation engine is based on.
 *
 * The SurveyIds are part of a contract with the Data Collection team. By agreeing on a survey ID with Data Collection,
 * the CalculationEngine can be confident of the the Question IDS that it can expect when interpreting the
 * SurveyResponse. In the event Data Collection makes a change to the survey the increments the ID, but doesn't change
 * the questions used, the new SurveyId should be added to CalculationEngine.
 */
public abstract class CalculationEngine {

	private final String version;
	private final Set<String> supportedSurveyIds;

	public CalculationEngine(String version, String ... supportedSurveyIds) {
		this.version = version;
		this.supportedSurveyIds = new HashSet<>(Arrays.asList(supportedSurveyIds));
	}
	/**
	 * @return Returns the version of the calculation engine.
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @returns a set of SurveyIds that are compatible with the Calculation Engine
	 */
	public Set<String> getSupportedSurveyIds() {
		return Collections.unmodifiableSet(supportedSurveyIds);
	}

	/**
	 * Calculates a Profile from the provided Survey Data.
	 *
	 * @param surveyResponse Raw survey response data
	 * @return the Profile derived from the SurveyResponse
	 * @throws CalculationException if a Profile cannot be created
	 */
	public abstract Profile calculate(SurveyResponse surveyResponse) throws CalculationException ;
}
