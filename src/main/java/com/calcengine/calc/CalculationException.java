package com.calcengine.calc;

/**
 * Thrown for errors that occur during calculations.
 */
public class CalculationException extends Exception {

	public CalculationException(String message) {
		super(message);
	}

	public CalculationException(String message, Throwable cause) {
		super(message, cause);
	}
}
