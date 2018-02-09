package com.calcengine.calc;

/**
 * Static methods for common Survey Coding schemes.
 */
public class Coding {

	/**
	 * Forward coding codes question responses in ascending order.
	 *
	 * @param response Question Response to code
	 * @return Response's coded value
	 */
	public static double forward(String response) {
		if (response == null) {
			return 0;
		}

		int value = Integer.parseInt(response);

		switch (value) {
			case 1: return 0.0;
			case 2: return 0.25;
			case 3: return 0.5;
			case 4: return 0.75;
			case 5: return 1.0;
			default: throw new IllegalArgumentException(value + " is not a valid response for forward coding");
		}
	}

	/**
	 * Backward coding codes question responses in descending order.
	 *
	 * @param response Question Response to code
	 * @return Response's coded value
	 */
	public static double backward(String response) {
		if (response == null) {
			return 0;
		}

		int value = Integer.parseInt(response);

		switch (value) {
			case 1: return 1.0;
			case 2: return 0.75;
			case 3: return 0.5;
			case 4: return 0.25;
			case 5: return 0.0;
			default: throw new IllegalArgumentException(value + " is not a valid response for backward coding");
		}
	}
}
